package com.example.hasu.playerbuddy.core;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class JSONSerializer {
    private Context mContext;
    public JSONSerializer(Context context) { mContext = context; }

    private String putSuffixToFilename(String filename, int suffix) {
        if(suffix == 1) {
            return filename + "_1";
        }
        else {
            int index = filename.lastIndexOf("_");
            return filename.substring(0, index) + "_" + Integer.toString(suffix);
        }
    }

    // returns actual filename, which might differ from the intended one if a file with that name
    // already existed!
    public String writeToFile(JSONObject jo, String filenameBase, String extension)
            throws IOException, JSONException {
        Writer writer = null;

        File existingFile = new File(mContext.getFilesDir(), filenameBase + extension);
        int suffix = 0;
        while(existingFile.exists()) {
            ++suffix;
            filenameBase = putSuffixToFilename(filenameBase, suffix);
            existingFile = new File(mContext.getFilesDir(), filenameBase + extension);
        }

        try {
            OutputStream out = mContext.openFileOutput(filenameBase + extension, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jo.toString());
            Log.d("Wrote JSON file", filenameBase + extension);
            return filenameBase + extension;
        }
        finally {
            if(writer != null) {
                writer.close();
            }
        }
    }

    public JSONObject readFile(String filename)
            throws FileNotFoundException, JSONException, IOException {
        BufferedReader reader = null;
        JSONObject jo = null;
        try {
            InputStream ins = mContext.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(ins));
            StringBuilder jsonString = new StringBuilder();
            String curLine = null;
            while((curLine = reader.readLine()) != null) {
                jsonString.append(curLine);
            }
            jo = new JSONObject(jsonString.toString());
        }
        finally {
            if(reader != null) {
                reader.close();
            }
        }
        return jo;
    }
}
