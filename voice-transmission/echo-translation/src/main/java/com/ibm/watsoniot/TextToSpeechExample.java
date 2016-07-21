/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.ibm.watsoniot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.CustomTranslation;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.CustomVoiceModel;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;

public class TextToSpeechExample {
	
	private final static String PROPERTIES_FILE_NAME = "/device.properties";
	
	public InputStream getSpeech(String text) throws IOException {
		
		/**
		 * Load device properties file
		 */
		Properties props = new Properties();
		try {
			props.load(TextToSpeechExample.class.getResourceAsStream(PROPERTIES_FILE_NAME));
		} catch (IOException e1) {
			System.err.println("Not able to read the properties file, exiting..");
			System.exit(-1);
		}
		
		/**
		 * Read individual parameter values from device properties file
		 */
			
		String tts_username = trimedValue(props.getProperty("tts-username"));
		String tts_password = trimedValue(props.getProperty("tts-password"));
		
	    //TextToSpeech service = new TextToSpeech("3b0f5473-fd6b-42fe-a93e-549e63b0aadb", "40XogEeBSm5f");
	    TextToSpeech service = new TextToSpeech(tts_username, tts_password);
	
	    //synthesize with custom voice model
	    InputStream in = service.synthesize(text, Voice.ES_LAURA, AudioFormat.WAV).execute();
	    return WaveUtils.reWriteWaveHeader(in);
	    //writeToFile(WaveUtils.reWriteWaveHeader(in), new File("c:\\output.wav"));
	}

	private static void writeToFile(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
    }
	
	/**
	 * Method that helps trim the output of the Device Properties File
	 * @param value
	 * @return
	 */
	private static String trimedValue(String value) {
		if(value == null || value == "") {
			return "";
		} else {
			return value.trim();
		}
	}
}
