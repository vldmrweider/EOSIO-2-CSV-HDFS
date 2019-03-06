import com.google.gson.Gson;

import java.io.*;

public class Main2 {

    public static void main(String[] args) throws IOException {

        Gson gson = new Gson();

        File f = new File("/media/vldmr/AAD247FFD247CE6F/gh/201901/2019-01-01-0.json");

        BufferedReader b = new BufferedReader(new FileReader(f));

        String readLine = "";

        while ((readLine = b.readLine()) != null) {

            GhBlock obj = gson.fromJson(readLine, GhBlock.class);
            System.out.println(obj.getActor().getLogin());

        }


        //    PrintWriter outCsv = new PrintWriter("gh.csv");




    }


}
