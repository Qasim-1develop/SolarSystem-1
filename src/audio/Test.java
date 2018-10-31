package audio;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {

        AudioMaster.init();
        AudioMaster.setListenerData(0, 0, 0);

        int buffer = AudioMaster.loadSound("audio/SpaceOdyssey.wav");
        Source source = new Source();
        source.setLooping(true);
        source.play(buffer);

        char c = ' ';
        while(c != 'q'){
            c = (char)System.in.read();

            if(c == 'p'){
                if(source.isPlaying()){
                    source.pause();
                }
                else {
                    source.continuePlaying();
                }
            }
        }

        source.delete();
        AudioMaster.cleanUp();
    }
}
