package com.qainfotech.tap.training.snl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.BoardModel;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

public class NewTest {
	Board board;
	UUID uuid;
	BoardModel model;
	JSONObject data;
	JSONArray players;
	@Test(expectedExceptions=PlayerExistsException.class,priority=0)
	public void is_registered_same_name() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException
	{
		board=new Board();
		board.registerPlayer("Abc");
		board.registerPlayer("Abc");
		
	}
	@Test(expectedExceptions=MaxPlayersReachedExeption.class,priority=1)
	public void is_registered_off_limit() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException
	{
		board=new Board();
		board.registerPlayer("Ajay");
		board.registerPlayer("Abc");
		board.registerPlayer("ram");
		board.registerPlayer("shyam");
		board.registerPlayer("mohan");
		
	}
	@Test(priority=2)
	public void true_registeration() throws FileNotFoundException, UnsupportedEncodingException, IOException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption
	{
		board=new Board();
		players=board.registerPlayer("Ajay");
		players=board.registerPlayer("Vijay");
		
	}
	@Test(expectedExceptions=GameInProgressException.class,priority=3)
	public void gameprogress() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException, InvalidTurnException
	{
		board.registerPlayer("ram");
		data=board.getData();
		JSONArray players=data.getJSONArray("players");
		JSONObject obj=players.getJSONObject(0);
		uuid=(UUID)obj.get("uuid");
		board.rollDice(uuid);
		board.registerPlayer("shyam");
	}
	
	
	@Test(expectedExceptions=InvalidTurnException.class,priority=4)
	public void invalid_turn() throws FileNotFoundException, UnsupportedEncodingException, InvalidTurnException
	{
		JSONArray players=data.getJSONArray("players");
		JSONObject obj=players.getJSONObject(0);
		uuid=(UUID)obj.get("uuid");
		JSONObject obj2=board.rollDice(uuid);
		Integer val=(Integer)obj2.get("dice");
		if(val.intValue()!=6)
		{
			board.rollDice(uuid);
		}
		
		
	}
	@Test(priority=5)
	public void validdelete() throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException
	{
		JSONArray players=data.getJSONArray("players");
		System.out.println("players schema"+players);
		JSONObject obj=players.getJSONObject(0);
		uuid=(UUID)obj.get("uuid");
		System.out.println("this is uuid"+uuid);
		System.out.println(obj);
		//uuid=UUID.randomUUID();
		System.out.println("uuid before: "+uuid);
		board.deletePlayer(uuid);
	}
	
	

	
	
}
