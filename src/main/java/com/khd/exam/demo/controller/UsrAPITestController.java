package com.khd.exam.demo.controller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Controller
public class UsrAPITestController {

	@RequestMapping("/usr/home/APITest")
	public String showAPITest() {
		return "usr/home/APITest";
	}

	@RequestMapping("/usr/home/APITest2")
	public String showAPITest2() {
		return "usr/home/APITest2";
	}
	
	@RequestMapping("/usr/home/APITest3")
	public String showAPITest3() {

		try {
			String url = "http://kipo-api.kipi.or.kr/openapi/service/trademarkInfoSearchService/getWordSearch";
			String serviceKey = "sd2%2Fw1FPMP7dCiLT1r8GNJatfwBCKhZfFVQAA3lNV55hr4o2tNP9B0NpNBn7iAGvAN8QwKTBfli73H%2Fdq7xZBw%3D%3D";
			String searchString = "love*롯데";

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document documentInfo = dBuilder.parse(url + "?ServiceKey=" + serviceKey + "&searchString=" + searchString);

			documentInfo.getDocumentElement().normalize();
			System.out.println("root tag : " + documentInfo.getDocumentElement().getNodeName());
			NodeList nList = documentInfo.getElementsByTagName("item");
			System.out.println("파싱할 tag수 : " + nList.getLength());

			for(int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("indexNo : " + getTagValue("indexNo", eElement));
					System.out.println("applicationDate : " + getTagValue("applicationDate", eElement));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "usr/home/APITest3";
	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if(nValue == null) {
			return null;
		}
		return nValue.getNodeValue();
	}
	
}