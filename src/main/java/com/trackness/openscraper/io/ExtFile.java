package com.trackness.openscraper.io;

import com.trackness.openscraper.structure.Tournament;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExtFile {

    private static final Schema<Tournament> TOURNAMENT_SCHEMA = RuntimeSchema.getSchema(Tournament.class);

    public static Tournament deserialize(String fileName) {
        File inputFile = new File(fileName);
        Tournament tournament = TOURNAMENT_SCHEMA.newMessage();
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            byte[] fileContent = new byte[(int) inputFile.length()];
//            TODO - Solve warning on .read
            fileInputStream.read(fileContent);
            ProtostuffIOUtil.mergeFrom(fileContent, tournament, TOURNAMENT_SCHEMA);
            try {
                fileInputStream.close();
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        }
        return tournament;
    }

    public static void serializeAndSave(String fileName, Tournament tournament) {
        try {
//            TODO - Write to buffer upon initialisation
            LinkedBuffer buffer = LinkedBuffer.allocate(512);
//            TODO - Timestamp output file
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName));
            fileOutputStream.write(ProtostuffIOUtil.toByteArray(tournament, TOURNAMENT_SCHEMA, buffer));
            buffer.clear();
            try {
                fileOutputStream.close();
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("ExtFile not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while writing file " + ioe);
        }
    }
}
