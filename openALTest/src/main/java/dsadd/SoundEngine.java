package dsadd;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

public class SoundEngine {

	public static void init() {

		// Can call "alc" functions at any time
		long device = ALC10.alcOpenDevice((ByteBuffer) null);
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);

		long context = ALC10.alcCreateContext(device, (IntBuffer) null);
		ALC10.alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
		// Can now call "al" functions

//		// Initialization
//		String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
//		long device = ALC10.alcOpenDevice(defaultDeviceName);
//
//		System.out.println("Device: " + device);
//
//		int[] attributes = { 0 };
//		long context = ALC10.alcCreateContext(device, attributes);
//
//		ALC10.alcMakeContextCurrent(context);
//
//		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
//		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
//
//		if (alCapabilities.OpenAL10) {
//			// OpenAL 1.0 is supported
//			System.out.println("Supported");
//		}

		AL10.alListener3f(AL10.AL_POSITION, 0, 0, 0);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);

		int sourceID = AL10.alGenSources();
		AL10.alSourcef(sourceID, AL10.AL_GAIN, 1);
		AL10.alSourcef(sourceID, AL10.AL_PITCH, 1);
		AL10.alSource3f(sourceID, AL10.AL_POSITION, 0, 0, 0);

		int buffer = loadSound("test.wav");

		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceID);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}

//					// Terminate OpenAL
//			alDeleteSources(sourcePointer);
//			alDeleteBuffers(bufferPointer);
//			alcDestroyContext(context);

		int error = AL10.alGetError();
		if (error != AL10.AL_NO_ERROR) {
			// Handle the error
			System.out.println("ERROR");
		}

		ALC10.alcCloseDevice(device);
	}

	public static int loadSound(String file) {
		int buffer = AL10.alGenBuffers();
		WaveData waveData = WaveData.create(file);
		AL10.alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
//		waveData.dispose();
		return buffer;
	}
}
