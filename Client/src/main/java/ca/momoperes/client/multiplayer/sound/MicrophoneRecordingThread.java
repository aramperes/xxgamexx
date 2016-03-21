package ca.momoperes.client.multiplayer.sound;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.io.message.serverbound.S08VoiceChatInputMessage;

import javax.sound.sampled.*;

public class MicrophoneRecordingThread extends Thread {

    public static boolean recording = false;
    private final AudioFormat format = getFormat();

    @Override
    public void run() {
        try {
            DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine mic = (TargetDataLine) AudioSystem.getLine(micInfo);
            while (true) {
                mic.open(format);
                mic.start();
                while (recording == true) {
                    long time = System.currentTimeMillis();
                    byte tmpBuff[] = new byte[16];
                    int count = mic.read(tmpBuff, 0, tmpBuff.length);
                    if (count > 0) {
                        GameClient.instance.session.send(new S08VoiceChatInputMessage(tmpBuff));
                    }
                    System.out.println((System.currentTimeMillis() - time) + " ms.");
                }

            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public AudioFormat getFormat() {
        float sampleRate = 16000.0F;
        int sampleSizeBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;

        return new AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian);
    }
}
