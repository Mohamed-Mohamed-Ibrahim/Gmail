package com.example.Email;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailApplication.class, args) ;
//		String filePath = "D:\\mohassan5286@gmail.com\\SentMails\\sentMails.json";
//		deleteObjectsOlderThan30Days(filePath) ;
	}

//	public static void deleteObjectsOlderThan30Days(String filePath) {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			List<JsonNode> nodesToKeep = new ArrayList<>();
//
//			// Read the file line by line
//			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//				String line;
//				while ((line = br.readLine()) != null) {
//					JsonNode node = objectMapper.readTree(line);
//					String dateString = node.get("date").asText();
//					LocalDateTime objectDate = parseDateString(dateString);
//
//					if (!isOlderThan30Days(objectDate)) {
//						nodesToKeep.add(node);
//					}
//				}
//			}
//
//			// Write the nodes to keep back to the file
//			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
//				for (JsonNode node : nodesToKeep) {
//					bw.write(objectMapper.writeValueAsString(node));
//					bw.newLine();
//				}
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static LocalDateTime parseDateString(String dateString) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		return LocalDateTime.parse(dateString, formatter);
//	}
//
//	private static boolean isOlderThan30Days(LocalDateTime date) {
//		LocalDateTime now = LocalDateTime.now();
//		return now.minusDays(30).isAfter(date);
//	}
}