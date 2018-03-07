package minhasanotacoes.cursoandroid.com.minhasanotacoes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText texto;
    private ImageView botaoSalvar;
    private static final String NOME_ARQUIVO="arquivo_anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.textoId);
        botaoSalvar = (ImageView) findViewById(R.id.botaoSalvarId);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Ao clicar em salvar, pega o que foi digitado.
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this,"Anotação Salva com sucesso", Toast.LENGTH_SHORT).show();

            }
        });

//        Recuperar o que foi gravado

        if(lerArquivo() != null){

           texto.setText( lerArquivo() );
        }

    }

//    metodo para gravar informações no arquivo de texto
    private void gravarNoArquivo(String texto){

        try{

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(texto);
            outputStreamWriter.close();

//      ioexception é quando vc tenta fazer uma operação de input e output
        }catch(IOException e){
//          MainActivity é para saber de onde vei o o erro.
            Log.e("MaintActivity",e.toString());


        }
    }

    private String lerArquivo(){

        String resultado = "";


        try{

            //abrir o arquivo

            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if(arquivo != null){

            //ler o arquivo
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);

            // Gerar Buffer do arquivo lido, recupera as informações do arquivo.
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            /*Recuperar textos do arquivo - readLine = recupera as linhas salvas no arquivo
                 bufferedReader.readLine(); */


                String linhaArquivo="";

                while((linhaArquivo = bufferedReader.readLine())!= null){
            /*  enquanto tiver texto no arquivo ele vai fazer a leitura e quando não tiver retorna nulo.
                Utilizando o bufferedreader, vai ser lido a primeira linha do arquivo e vai ser armazenado em linhaArquivo
                Quando não exisitir mais linhas para serem  lidas, o bufferedReader.readLine() vai retornar nulo e não vai mais executar o while */

                    resultado += linhaArquivo;

                }
                arquivo.close();
            }
        }catch (IOException e){

            Log.v("MainActivity", e.toString());
        }
        return resultado;

    }

}
