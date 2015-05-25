package view;

import java.net.URL;

import model.CarDatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;

public class Utils {
	static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static ImageIcon createIcon(String path){
		URL imgUrl = System.class.getResource(path);
		
		if(imgUrl == null){
			logger.error("Nem sikerült betölteni a következő képet:" + path);
		}
		
		ImageIcon icon = new ImageIcon(imgUrl);
		
		return icon;
	}
}
