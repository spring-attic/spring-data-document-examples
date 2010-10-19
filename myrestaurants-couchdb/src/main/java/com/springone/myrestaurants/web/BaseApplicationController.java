package com.springone.myrestaurants.web;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springone.myrestaurants.dao.RestaurantDao;
import com.springone.myrestaurants.dao.UserAccountDao;
import com.springone.myrestaurants.domain.Restaurant;
import com.springone.myrestaurants.domain.UserAccount;

public class BaseApplicationController {

	@Autowired
	RestaurantDao restaurantDao;

	@Autowired
	UserAccountDao userAccountDao;

	@ModelAttribute("currentUserAccountId")
	public String populateCurrentUserName() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			if (securityContext.getAuthentication() != null) {
				String currentUser = securityContext.getAuthentication()
						.getName();
				UserAccount userAccount = userAccountDao.findByName(currentUser);
				if (userAccount != null) {
					return userAccount.getUserName();// getId().toString();
				} else {
					return "USER-ID-NOT-AVAILABLE";
				}
			} else {
				return "demouser";
			}
		} else {
			return "demouser";
		}
	}

	void addDateTimeFormatPatterns(Model model) {
		model.addAttribute(
				"userAccount_birthdate_date_format",
				DateTimeFormat.patternForStyle("S-",
						LocaleContextHolder.getLocale()));
	}

	protected Converter<Restaurant, String> getRestaurantConverter() {
		return new Converter<Restaurant, String>() {
			public String convert(Restaurant restaurant) {
				return new StringBuilder().append(restaurant.getName())
						.append(" ").append(restaurant.getCity()).append(" ")
						.append(restaurant.getState()).toString();
			}
		};
	}

}
