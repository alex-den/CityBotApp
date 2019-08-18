package com.example.telegram;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.service.CityService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

	private static final String BOT_NAME = "@funny_tourist_bot";
	private static final String BOT_TOKEN = "960911861:AAEnvQR6a0Pd6fUdKWrTR1HE8i692HEdDVY";
	private static final String NO_CONTENT = "Sorry, I don't have information about it =(";

	@PostConstruct
	public void registerBot() {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(this);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private final CityService cityService;

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String message = update.getMessage().getText();
			if ((update.getMessage().getChat().isUserChat())
					|| (update.getMessage().getChat().isGroupChat() && (message.contains(BOT_NAME)))) {
				String cityName = message.replace(BOT_NAME, "").trim();
				String answer = createAnswerMsgText(cityName);
				if (answer.isEmpty())
					answer = NO_CONTENT;
				sendMsg(update.getMessage().getChatId().toString(), answer);
			}
		}
	}

	public String createAnswerMsgText(String cityName) {
		return cityService.getInfoAboutCityByName(cityName);
	}

	public synchronized void sendMsg(String chatId, String s) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId);
		sendMessage.setText(s);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return BOT_NAME;
	}

	@Override
	public String getBotToken() {
		return BOT_TOKEN;
	}

}
