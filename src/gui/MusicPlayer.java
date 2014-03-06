package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import sun.audio.*;

/*
 * This class was taken from online. It represents much less that 15% of the overall
 * source code of the project. Used only for sound effects and music
 */
public class MusicPlayer {
	static AudioFormat audioFormat;
	  static AudioInputStream audioInputStream;
	  static SourceDataLine sourceDataLine;
	  static boolean stopPlayback ;

	  static Thread t;

	public static boolean inGameSound = true;
	
	public static void setInGameSound(boolean b){
		inGameSound =b;
	}
	
	public static boolean inGameSound(){
		return inGameSound;
	}
	
	public static void setStopPlayBack(Boolean b){
		stopPlayback = true;
	}
	
	
	public static void playMusic(String file){
		AudioPlayer audioPlayer = AudioPlayer.player;
		AudioStream audioStream;
		AudioData audioData;
		
		AudioDataStream loop= null;
		
		try{
			audioStream = new AudioStream(new FileInputStream(file));
			
			audioData =audioStream.getData();
			
			loop = new AudioDataStream(audioData);
			

		}
		catch(IOException ex){ex.printStackTrace();}
		
		if(inGameSound==true){
			audioPlayer.start(loop);
		}
	}
	
	public static void playRandomCard(){
		AudioPlayer audioPlayer = AudioPlayer.player;
		AudioStream audioStream;
		AudioData audioData;
		
		AudioDataStream loop= null;
		
		try{
			Random r = new Random();
			int i = r.nextInt(4);
			String file = null;
			if(i==0){file ="src/sounds/p1.wav";}
			else if(i==1){file ="src/sounds/p2.wav";}
			else if(i==2){file ="src/sounds/p3.wav";}
			else if(i==3){file ="src/sounds/p4.wav";}
			
			
			audioStream = new AudioStream(new FileInputStream(file));
			
			audioData =audioStream.getData();
			
			loop = new AudioDataStream(audioData);
			
			
			
			//audioPlayer.start(loop);
		}
		catch(IOException ex){ex.printStackTrace();}
		

		if(inGameSound==true){
			audioPlayer.start(loop);
		}
		
	}

	/* This code was borrowed from the webSite http://www.developer.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
	 * 
	 * 
	 */
	
	public static void playAudio(String s){
		
		if(inGameSound){
		class PlayThread extends Thread{
	    	  byte tempBuffer[] = new byte[10000];

	    	  public void run(){
	    	    try{
	    	      sourceDataLine.open(audioFormat);
	    	      sourceDataLine.start();

	    	      int cnt;
	    	      //Keep looping until the input read method
	    	      // returns -1 for empty stream or the
	    	      // user clicks the Stop button causing
	    	      // stopPlayback to switch from false to
	    	      // true.
	    	      while((cnt = audioInputStream.read(
	    	           tempBuffer,0,tempBuffer.length)) != -1
	    	                       && stopPlayback == false){
	    	        if(cnt > 0){
	    	          //Write data to the internal buffer of
	    	          // the data line where it will be
	    	          // delivered to the speaker.
	    	          sourceDataLine.write(
	    	                             tempBuffer, 0, cnt);
	    	        }//end if
	    	      }//end while
	    	      //Block and wait for internal buffer of the
	    	      // data line to empty.
	    	      sourceDataLine.drain();
	    	      sourceDataLine.close();

	    	      //Prepare to playback another file
	    	      //stopBtn.setEnabled(false);
	    	      //playBtn.setEnabled(true);
	    	      stopPlayback = false;
	    	    }catch (Exception e) {
	    	      e.printStackTrace();
	    	      System.exit(0);
	    	    }//end catch
	    	  }//end run
	    	}//end inner class PlayThread
	    	//===================================//
		try{
		      File soundFile =new File(s);
		      audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		      audioFormat = audioInputStream.getFormat();
		      
		      DataLine.Info dataLineInfo = new DataLine.Info(
		                            SourceDataLine.class,
		                                    audioFormat);

		      sourceDataLine =
		             (SourceDataLine)AudioSystem.getLine(
		                                   dataLineInfo);

		      //Create a thread to play back the data and
		      // start it running.  It will run until the
		      // end of file, or the Stop button is
		      // clicked, whichever occurs first.
		      // Because of the data buffers involved,
		      // there will normally be a delay between
		      // the click on the Stop button and the
		      // actual termination of playback.
		      
		      Thread t =  new PlayThread();
		      t.start();
		    }catch (Exception e) {
		      e.printStackTrace();
		      System.exit(0);
		    }//end catch
		}
	}
	
	
	public static void main(String[] args){
		
		//inGameSound =false;
		//playMusic("src/sounds/p1.wav");
		
		
		MusicPlayer.playAudio("src/sounds/classical.wav");
		//playAudio("src/sounds/applause.wav");
		//playRandomCard();
		
		//playAudio("src/sounds/hail_to_the_chief.wav");
		/*
		 long t0, t1;
			t0 = System.currentTimeMillis();
			do{
				t1=System.currentTimeMillis();
			}
			while(t1-t0<4000);
			
			
		stopPlayback = true;
		
		playAudio("src/sounds/classical.wav");
		*/
		//playAudio("src/sounds/hail_to_the_chief.wav");
		
		//new AePlayWave("src/sounds/classical.wav").start();
	}

}
