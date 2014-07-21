package com.entelo.service.crunchbase;

import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * getting elements from html document
 * 
 * @author akbar
 * 
 */
class Parser {

	static String getScreenName(Document doc) {
		Elements screenNameElements = doc
				.getElementsByClass("name-and-controls");
		Element screenNameElement = screenNameElements.first();
		return screenNameElement == null ? "" : screenNameElement.text();
	}

	static String getGivenName(Document doc) {
		Element givenNameElement = doc.getElementById("my-username");
		return givenNameElement == null ? "" : givenNameElement.text();
	}

	static String getIndustry(Document doc) {
		Element industryElement = doc.getElementById("work-industry");
		return industryElement == null ? "" : industryElement.text();
	}

	static String getMyLocation(Document doc) {
		Element locationElement = doc.getElementById("my-location");
		return locationElement == null ? "" : locationElement.text();
	}

	static String getMyProfileImageUrl(Document doc) {
		Element profileImage = doc
				.getElementsByClass("home")
				.first().getElementsByTag("img").first();
		return profileImage == null ? "" : profileImage.attr("src");
	}

	static String getMyCertifications(Document doc) {
		Element locationElement = doc.getElementById("my-certifications");
		return locationElement == null ? "" : locationElement.text();
	}

	static String getMyWebSite(Document doc) {
		String wesitesNameList = "";
		Element myWebsites = doc.getElementById("my-website");
		Elements myWebsitesUrl = myWebsites.getElementsByTag("a");
		Iterator<Element> itrUrl = myWebsitesUrl.iterator();

		while (itrUrl.hasNext()) {
			Element myWebsiteUrl = itrUrl.next();
			String url = myWebsiteUrl.attr("href");
			wesitesNameList += url + ",";
		}
		// remove last comma
		try {
			wesitesNameList = wesitesNameList.substring(0,
					wesitesNameList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + wesitesNameList + "\"";
	}

	static String getWorkTitle(Document doc) {
		Element currentPositiontElement = doc.getElementById("work-title");
		return currentPositiontElement == null ? "" : currentPositiontElement
				.text();
	}

	static String getSummary(Document doc) {
		String summary = "";
		Elements summaryElements = doc.getElementsByClass("summary");
		Element summaryElement = summaryElements.first();
		Elements pElements = summaryElement.getElementsByTag("p");
		if (pElements != null) {
			summary = "\"" + pElements.html().replace("<br />", ",") + "\"";
		}
		return summary;
	}

	static String getInterests(Elements interrstList) {
		String namesList = "";
		Element interstsPartElement = interrstList.first();
		if (interstsPartElement == null)
			return namesList;

		Element headingElement = interstsPartElement.getElementsByTag("h3")
				.first();
		if (headingElement == null)
			return namesList;
		if (!headingElement.text().trim().startsWith("Interests")) {
			return namesList;
		}

		Element ulElement = interstsPartElement.getElementsByTag("ul").first();
		if (ulElement == null)
			return namesList;

		Iterator<Element> itr = ulElement.children().iterator();

		while (itr.hasNext()) {
			Element userElement = itr.next();
			Element userNameElement = userElement.getElementsByTag("a").first();
			if (userNameElement == null)
				continue;
			String interest = userNameElement.text();
			namesList += interest + ",";
		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

	static String getGroups(Elements grouptList) {
		String namesList = "";
		Element interstsPartElement = grouptList.first();
		if (interstsPartElement == null)
			return namesList;

		Elements headingElement = interstsPartElement.getElementsByTag("a");
		if (headingElement == null)
			return namesList;

		Iterator<Element> itr = headingElement.iterator();

		while (itr.hasNext()) {
			Element groupElement = itr.next();
			String group = groupElement.attr("href");
			namesList += group + ",";
		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

	static String getBadges(Elements grouptList) {
		String namesList = "";
		Element interstsPartElement = grouptList.first();
		if (interstsPartElement == null)
			return namesList;

		Elements headingElement = interstsPartElement.getElementsByTag("img");
		if (headingElement == null)
			return namesList;

		Iterator<Element> itr = headingElement.iterator();

		while (itr.hasNext()) {
			Element groupElement = itr.next();
			String group = groupElement.attr("alt");
			namesList += group + ",";
		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

	static String getUsers(Elements usersList, String listHeading) {
		String namesList = "";
		Iterator<Element> itrUsers = usersList.iterator();
		Element usersPartElement = null;
		while (itrUsers.hasNext()) {
			usersPartElement = itrUsers.next();
			if (usersPartElement == null)
				return namesList;

			Element headingElement = usersPartElement.getElementsByTag("h3")
					.first();
			if (headingElement == null)
				return namesList;
			if (headingElement.text().trim().startsWith(listHeading)) {
				break;
			} else {
				usersPartElement = null;
				continue;
			}
		}

		if (usersPartElement == null)
			return namesList;

		Element usersElement = usersPartElement.getElementsByTag("ul").first();
		if (usersElement == null)
			return namesList;

		Iterator<Element> itr = usersElement.children().iterator();

		while (itr.hasNext()) {
			Element userElement = itr.next();
			Element userNameElement = userElement.getElementsByTag("a").first();
			if (userNameElement == null)
				continue;
			String href = userNameElement.attr("href");
			int startIndex = href.lastIndexOf("/") + 1;
			int lastIndex = href.length();
			String user = href.substring(startIndex, lastIndex).trim();
			namesList += user.replace("%20", " ") + ",";
		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

	static String getPastPositions(Element projectstList) {
		String namesList = "";
		Elements headingElements = projectstList
				.getElementsByClass("project-card");
		if (headingElements == null)
			return namesList;
		if (headingElements.isEmpty()) {
			return namesList;
		}

		Iterator<Element> itr = headingElements.iterator();

		while (itr.hasNext()) {
			Element projectElement = itr.next();
			Element projectNameElement = projectElement.getElementsByTag("h3")
					.first();
			if (projectNameElement == null)
				continue;
			String projectName = projectNameElement.text();

			Element projectSummeryElement = projectElement.getElementsByClass(
					"summary").first();
			String projectSummeryText = projectSummeryElement == null ? ""
					: projectSummeryElement.text().trim();

			Element projectCompletionStatusElement = projectElement
					.getElementsByClass("status").first();

			String projectCompletionStatusText = projectCompletionStatusElement == null ? ""
					: projectCompletionStatusElement.text().trim();
			if (!projectCompletionStatusText
					.equalsIgnoreCase("Work in Progress")) {
				namesList += projectName + "; Status["
						+ projectCompletionStatusText + "], Summbery["
						+ projectSummeryText + "]" + ",";
			}

		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

	static String getCurrentPositions(Element projectstList) {
		String namesList = "";
		Elements headingElements = projectstList
				.getElementsByClass("project-card");
		if (headingElements == null)
			return namesList;
		if (headingElements.isEmpty()) {
			return namesList;
		}

		Iterator<Element> itr = headingElements.iterator();

		while (itr.hasNext()) {
			Element projectElement = itr.next();
			Element projectNameElement = projectElement.getElementsByTag("h3")
					.first();
			if (projectNameElement == null)
				continue;
			String projectName = projectNameElement.text();

			Element projectSummeryElement = projectElement.getElementsByClass(
					"summary").first();
			String projectSummeryText = projectSummeryElement == null ? ""
					: projectSummeryElement.text().trim();

			Element projectCompletionStatusElement = projectElement
					.getElementsByClass("status").first();

			String projectCompletionStatusText = projectCompletionStatusElement == null ? ""
					: projectCompletionStatusElement.text().trim();
			if (projectCompletionStatusText
					.equalsIgnoreCase("Work in Progress")) {
				namesList += projectName + "; Status["
						+ projectCompletionStatusText + "], Summbery["
						+ projectSummeryText + "]" + ",";
			}

		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

	static String getAllPositions(Element projectstList) {
		String namesList = "";
		Elements headingElements = projectstList
				.getElementsByClass("project-card");
		if (headingElements == null)
			return namesList;
		if (headingElements.isEmpty()) {
			return namesList;
		}

		Iterator<Element> itr = headingElements.iterator();

		while (itr.hasNext()) {
			Element projectElement = itr.next();
			Element projectNameElement = projectElement.getElementsByTag("h3")
					.first();
			if (projectNameElement == null)
				continue;
			String projectName = projectNameElement.text();

			Element projectSummeryElement = projectElement.getElementsByClass(
					"summary").first();
			String projectSummeryText = projectSummeryElement == null ? ""
					: projectSummeryElement.text().trim();

			Element projectCompletionStatusElement = projectElement
					.getElementsByClass("status").first();

			String projectCompletionStatusText = projectCompletionStatusElement == null ? ""
					: projectCompletionStatusElement.text().trim();
			namesList += projectName + "; Status["
					+ projectCompletionStatusText + "], Summbery["
					+ projectSummeryText + "]" + ",";

		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

	static String getAllTechnologies(Element projectstList) {
		String namesList = "";
		Elements headingElements = projectstList.getElementsByTag("h2");
		if (headingElements == null)
			return namesList;
		if (headingElements.isEmpty()) {
			return namesList;
		}

		Iterator<Element> itr = headingElements.iterator();

		while (itr.hasNext()) {
			Element categoryElement = itr.next();
			String categoryText = categoryElement.text();
			namesList += categoryText + ",";
		}
		// remove last comma
		try {
			namesList = namesList.substring(0, namesList.length() - 1);
		} catch (Exception e) {
		}
		return "\"" + namesList + "\"";

	}

}
