package ca.momoperes.client.multiplayer.sound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;

public class VoiceChatThread extends Thread {

    public static ByteBuf buf = Unpooled.buffer();

    @Override
    public void run() {
        while (true) {

            try {
                byte[] data = buf.readBytes(256).array();
                System.out.println("Not empty.");
                DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, getAudioFormat());
                SourceDataLine speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
                speaker.open(getAudioFormat());
                speaker.start();
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                AudioInputStream ais = new AudioInputStream(bais, getAudioFormat(), data.length);
                int bytesRead = 0;
                if ((bytesRead = ais.read(data)) != -1) {
                    System.out.println("Writing to audio output.");
                    speaker.write(data, 0, bytesRead);
                }
                ais.close();
                bais.close();
            } catch (Exception e) {
                continue;
            }
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleSizeBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;

        return new AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian);
    }
}
