package com.example.callendar;

/**
 * Created by feiranhu on 2020-03-28.
 */

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v1.*;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AudioTranscriptor {

    private int sample_rate;

    public AudioTranscriptor(){
        this.sample_rate = 48000;
    }

    public AudioTranscriptor(int sample_rate){
        this.sample_rate = sample_rate;
    }


    public String transcript(final String audioPath) throws Exception{
        long start_time = System.currentTimeMillis();
        Path path = Paths.get(audioPath);
        String transcript = "";
        byte[] data = Files.readAllBytes(path);
        int lenPerMin = (this.sample_rate*60);
        //System.out.println(lenPerMin);
        ArrayList<ArrayList<Byte>> clipLists = new ArrayList<>();
        ArrayList<Byte> clip = new ArrayList<>();
        // transcript
        int count = 0;
        for( byte dataByte: data){
            if (count == lenPerMin) {
                clipLists.add(new ArrayList<>(clip));
                clip.clear();
                count = 0;
            }
            clip.add(dataByte);
            count += 1;
        }
        if(clip.size() != 0){
            clipLists.add(clip);
        }
        //
        boolean first = true;
        for(ArrayList<Byte> clipdata : clipLists){
            //System.out.println(clipdata.size());
            Byte[] inputClipByte = clipdata.toArray(new Byte[clipdata.size()]);
            byte[] inputClip = toPrimitives(inputClipByte);
            String currentTranscript = SyncTranscriptClip(inputClip);
            if (currentTranscript.length() != 0 &&  !first){
                transcript += " "+currentTranscript;
            }else{
                transcript += currentTranscript;
                first = false;
            }
        }
        System.out.println(transcript);
        final float elasped_time = (System.currentTimeMillis()- start_time) / 1000F;
        System.out.println("Transcription took "+elasped_time+" seconds to complete.");
        return transcript;

    }

    private static  byte[] toPrimitives(Byte[] oBytes)
    {
        byte[] bytes = new byte[oBytes.length];

        for(int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }

        return bytes;
    }

    private void splitByteArray (byte [] fileContent, byte [] fileContentLeft, byte [] fileContentRight) {

        for (int i = 0; i < fileContent.length; i += 4) {

            fileContentLeft[i] = fileContent[i];
            fileContentLeft[i + 1] = fileContent[i + 1];
            fileContentRight[i + 2] = fileContent[i + 2];
            fileContentRight[i + 3] = fileContent[i + 3];

        }
    }

    private String SyncTranscriptClip(final byte[] data) throws Exception{
        try (SpeechClient speechClient = SpeechClient.create()) {
            System.out.println("Launch audio transcript sequence (Syncronize) **clip-wise**...");
            // Reads the audio file into memory
            ByteString audioBytes = ByteString.copyFrom(data);
            // Builds the sync recognize request
            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(AudioEncoding.LINEAR16)
                    .setSampleRateHertz(48000)
                    .setEnableAutomaticPunctuation(true)
                    .setLanguageCode("en-US")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // Performs speech recognition on the audio file
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            String transcript = "";

            System.out.println("Audio transcript complete **clip-wise**...");
            for (SpeechRecognitionResult result : results) {
                // There can be several alternative transcripts for a given chunk of speech. Just use the
                // first (most likely) one here.
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                //System.out.printf("Transcription: %s%n", alternative.getTranscript());
                transcript += alternative.getTranscript();
            }
            //System.out.printf("Transcription: %s", transcript);
            return transcript;
        }

    }

}

