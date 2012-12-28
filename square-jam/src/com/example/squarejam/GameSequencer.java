/* Copyright © 2012 Stephen Molyneaux <@SHMolyneaux>
 * 
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */

package com.example.squarejam;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class GameSequencer {
	private static final int maxCols = 8;
	private static final int maxRows = 6;
	
	private static final float minGameTime = 30.0f;
	
	private MyGdxGame game;
	private TweenManager manager;
	
	private float elapsedGameTime = 0;
	private float stageEndTime = 0;
	
	private int stageMode = STANDARD_MODE;
	private int numOfStages = 0;
	
	private static final int STANDARD_MODE = 0;
	private static final int FLASH_MODE = 1;
	private static final int CHECKERED_MODE = 2;
	
	public GameSequencer(MyGdxGame myGame, TweenManager myManager) {
		game = myGame;
		manager = myManager;
	}
	
	private void defaultSquareTouch(Square block) {
		Tween.call(new SquareColorChangeCallback(block, game))	//Randomly assigns color
			.delay(block.getAnimationTime())
			.start(manager);
	}
	
	public void squareTouched(Square block) {
		defaultSquareTouch(block);
	}
	
	private void createNewStage() {
		stageMode = STANDARD_MODE;
		numOfStages++;
		
		int rows = 0;
		int cols = 0;
		
		//The following chances technically change based on the chances of previous ones
		//Therefore special layouts should be ordered from least to most likely
		if (Math.random() < 0.04) { //1 in 25 chance
			//Single tile is instantly replaced
			rows = 1;
			cols = 1;
			stageMode = FLASH_MODE;
		}
		
		else if (Math.random() < 0.1) { //1 in 10 chance
			//Xylophone
			rows = 1;
			cols = 3 + (int) (Math.random()*maxCols - 2); //returns between 3 and maxCols
		}
		
		else {
			//Standard assortment of squares of various grid dimesions based on how far along the game has progressed
			
			if (numOfStages == 1) {
				rows = 2 + (int) (Math.random()*2); //returns 2 or 3
				cols = 2 + (int) (Math.random()*2); //returns 2 or 3
				
				System.out.println("Starter");
			}
			
			else {
				if (numOfStages + 3 < maxRows) {
					rows = numOfStages + 1 + (int) (Math.random()*3); //returns between numOfStages + 1 and numOfStages + 3
				}
				
				else {
					rows = maxRows - 3 + (int) (Math.random()*4); //returns between maxRows - 3 and maxRows
					System.out.println("Max Rows");
				}
				
				if (numOfStages + 3 < maxCols) {
					cols = numOfStages + 1 + (int) (Math.random()*3); //returns between numOfStages + 1 and numOfStages + 3
				}
				
				else {
					cols = maxCols - 3 + (int) (Math.random()*4); //returns between maxCols - 3 and maxCols
					System.out.println("Max Cols");
				}
			}
		}
		
		if (Math.random() < 0.08 && stageMode == STANDARD_MODE) { //2 in 25
			stageMode = CHECKERED_MODE;
		}
		
		game.createGrid( rows, cols );
		stageEndTime = (float) (elapsedGameTime + 5.0f + Math.random()*(rows+cols));
		
		System.out.println("New Grid: "+rows+" by "+cols);
	}
	
	public void update(float time) {
		elapsedGameTime += time;
		
		if (elapsedGameTime > stageEndTime) {
			if (elapsedGameTime > minGameTime + Math.random()*20 + Math.random()*20) { //Two separate dice rolls
				game.endGame();
				return;
			}
			
			createNewStage();
		}
	}
	
	public float elapsedGameTime() {
		return elapsedGameTime;
	}
	
	public void start() {
		elapsedGameTime = 0;
		numOfStages = 0;
		createNewStage();
	}
	
	public void restart() {
		start();
	}

	public void gridCreated(List<Square> squareList) {
		if (stageMode == FLASH_MODE) {
			for (int i=0; i<squareList.size(); i++) {
				squareList.get(i).setTiming(0, 0, 0);
			}
			
			System.out.println("Flash Mode!");
		}
		
		else if (stageMode == CHECKERED_MODE) {
			for (int i=0; i<squareList.size(); i++) {
				game.changeColor( squareList.get(i), i%game.getNumOfPlayers());
			}
			
			System.out.println("Checkered Mode!");
		}
	}
}
