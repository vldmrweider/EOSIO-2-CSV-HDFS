import com.google.gson.Gson;

import java.io.*;

public class Main2 {

    public static void main(String[] args) throws IOException {

        Gson gson = new Gson();

        PrintWriter outCsv = new PrintWriter("/media/vldmr/AAD247FFD247CE6F/gh/gh.csv");
       // final File folder = new File("/media/vldmr/AAD247FFD247CE6F/gh/2018");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {

                System.out.println(file.getName());
            parsefile(gson, outCsv, file.getAbsolutePath());

                file.delete();
            }
        }

        outCsv.close();

    }



    private static void parsefile(Gson gson, PrintWriter outCsv, String fname) throws IOException {

        File f = new File(fname);
      //  /media/vldmr/AAD247FFD247CE6F/gh/201901/2019-01-01-0.json

        BufferedReader b = new BufferedReader(new FileReader(f));

        String readLine = "";

        while ((readLine = b.readLine()) != null) {

            GhBlock obj = gson.fromJson(readLine, GhBlock.class);
            outCsv.println(String.format("%s;%s;%s;%s",obj.getActor().getId(),obj.getActor().getLogin(),obj.getRepo().getId(),obj.getType()));
            //System.out.println(String.format("%s;%s;%s;%s",obj.getActor().getId(),obj.getActor().getLogin(),obj.getRepo().getId(),obj.getType()));

        }

    }


}
