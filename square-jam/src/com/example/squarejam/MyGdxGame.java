/* Copyright © 2012 Stephen Molyneaux <@SHMolyneaux>
 * 
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */

package com.example.squarejam;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame implements ApplicationListener  {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private GameSequencer sequencer;
	
	private Texture texOrange;
	private Texture texBlue;
	private Texture texGreen;
	private Texture texPurple;
	
	private Texture S;
	private Texture Q;
	private Texture U;
	private Texture A;
	private Texture R;
	private Texture E;
	
	private Texture J;
	private Texture JAM_A;
	private Texture M;
	
	private Texture two;
	private Texture three;
	private Texture four;
	private Texture question;
	
	private Texture tablet;
	private Texture light_hand;
	private Texture tan_hand;
	private Texture medium_hand;
	private Texture dark_hand;
	
	private Texture help_bg;
	private Texture twitt;
	
	private Sound bloop;
	
	private float squareWidth = 0.2f;
	private float squareHeight = 0.2f;
	
	private int squareRows;
	private int squareCols;
	
	private List<Square> square = new ArrayList<Square>();
	private List<Sprite> displayList = new ArrayList<Sprite>();
	
	private TweenManager manager = new TweenManager();
	
	private float gameTimeSeconds = 0;
	private int gameStep = 0;
	private int gameState = INTRO_MENU;
	
	private boolean scoreScreenPaused = false;
	
	private int orangeScore = 0;
	private int blueScore = 0;
	private int greenScore = 0;
	private int purpleScore = 0;
	
	private int numOfPlayers = 2;
	
	private final static int INTRO_MENU = 0;
	private final static int HELP_MENU = 1;
	private final static int PLAY_STATE = 2;
	private final static int SCORE_SCREEN = 3;
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		sequencer = new GameSequencer(this, manager);
		
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		texOrange = getTexture("data/orange.png");
		texBlue = getTexture("data/blue.png");
		texGreen = getTexture("data/green.png");
		texPurple = getTexture("data/purple.png");
		
		//=====================================================
		S = getTexture("data/S.png");
		Q = getTexture("data/Q.png");
		U = getTexture("data/U.png");
		A = getTexture("data/A.png");
		R = getTexture("data/R.png");
		E = getTexture("data/E.png");
		
		J = getTexture("data/J.png");
		JAM_A = getTexture("data/JAM_A.png");
		M = getTexture("data/M.png");
		
		two = getTexture("data/two.png");
		three = getTexture("data/three.png");
		four = getTexture("data/four.png");
		question = getTexture("data/question.png");
		
		light_hand = getGoodTexture("data/light_hand.png");
		tan_hand = getGoodTexture("data/tan_hand.png");
		medium_hand = getGoodTexture("data/medium_hand.png");
		dark_hand = getGoodTexture("data/dark_hand.png");
		
		tablet = getGoodTexture("data/tablet.png");
		
		help_bg = getTexture("data/help_bg.png");
		twitt = getTexture("data/twitt.png");
		//=====================================================
		
		intro();
		
		bloop = Gdx.audio.newSound(Gdx.files.internal("data/bloop.wav"));
		
		TouchProcessor inputProcessor = new TouchProcessor(this);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texOrange.dispose();
		texBlue.dispose();
		texGreen.dispose();
		texPurple.dispose();
		bloop.dispose();
		
		S.dispose();
		Q.dispose();
		U.dispose();
		A.dispose();
		R.dispose();
		E.dispose();
		
		J.dispose();
		JAM_A.dispose();
		M.dispose();
		
		question.dispose();
		
		two.dispose();
		three.dispose();
		four.dispose();
		
		help_bg.dispose();
	}

	@Override
	public void render() {
		manager.update(1.0f/30.0f);
		
		if (gameState == PLAY_STATE) {
			float deltaTime = Gdx.graphics.getDeltaTime();
			gameTimeSeconds += deltaTime;
			
			sequencer.update(deltaTime);
		}
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			for (int i=0; i<square.size(); i++) {
				square.get(i).draw(batch);
			}
			
			for (int i=0; i<displayList.size(); i++) {
				displayList.get(i).draw(batch);
			}
		batch.end();
	}

	public void touch(int x, int y) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		Vector3 worldCoordinates = new Vector3(x, y, 0);
		camera.unproject(worldCoordinates);
		
		if (gameState == INTRO_MENU) {
			//1 2 and 3 are the player 2 3 and 4 buttons, 0 is the help button
			
			for (int i=1; i<=4; i++) {
				if (worldCoordinates.x > displayList.get(i).getX() &&
						worldCoordinates.x < displayList.get(i).getX() + displayList.get(i).getWidth() &&
						worldCoordinates.y > displayList.get(i).getY() &&
						worldCoordinates.y < displayList.get(i).getY() + displayList.get(i).getHeight()) {
					if (i==1) {
						help();
						break;
					}
					
					else {
						numOfPlayers = i;
						
						sequencer.start();
						gameState = PLAY_STATE;
						
						break;
					}
				}
			}
		}
		
		else if (gameState == PLAY_STATE) {
			int squareNum = 0;
			
			squareNum  = (int) ((worldCoordinates.x + 0.5f)/squareWidth); //Column
			squareNum += (int) ( ( (worldCoordinates.y + (h/(2*w)) )/squareHeight - squareRows)*-1 )*squareCols; //Row
			
			try {
				if (square.get(squareNum).touch(this, manager)) {
					bloop.play(0.5f, (float) (Math.random()*0.4+1.0f), 0);
					
					switch (square.get(squareNum).getSquareColor()) {
						case Square.colorOrange: orangeScore++; break;
						case Square.colorBlue: blueScore++; break;
						case Square.colorGreen: greenScore++; break;
						case Square.colorPurple: purpleScore++; break;
						default: break;
					}
					
					sequencer.squareTouched(square.get(squareNum));
				}
			}
			
			catch (IndexOutOfBoundsException e) {
				//Don't crash
			}
			
			if (gameStep == 3 && gameTimeSeconds>20) {
				gameStep = 0;
				gameTimeSeconds = 0;
				
				createGridCallback(2,2);
			}
		}
		
		else if (gameState == SCORE_SCREEN && !scoreScreenPaused) {
			intro();
		}
		
		else if (gameState == HELP_MENU) {
			intro();
		}
	}
	
	private Texture getTexture(String textureFileString) {
		Texture textureObject = new Texture(Gdx.files.internal(textureFileString));
		textureObject.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		return textureObject;
	}
	
	private Texture getGoodTexture(String textureFileString) {
		Texture textureObject = new Texture(Gdx.files.internal(textureFileString));
		textureObject.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		return textureObject;
	}
	
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	public int getGridWidth() {
		return squareCols;
	}
	
	public int getGridHeight() {
		return squareRows;
	}
	
	public Square getGridSquare(int squareNum) {
		return square.get(squareNum);
	}
	
	public Square getGridSquare(int row, int col) {
		return getGridSquare(row * squareCols + col);
	}
	
	public void clearGrid() {
		square.clear();
	}
	
	public void createGrid(int rows, int cols) {
		destroyGrid(rows, cols); //Creates grid on callback
	}
	
	public void createGridCallback(int rows, int cols) {
		displayList.clear();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		squareRows = rows;
		squareCols = cols;
		
		squareWidth = 1.0f/squareCols;
		squareHeight = (h/w)/squareRows;
		
		square.clear();
		
		for (int row=0; row<squareRows; row++) {
			for (int col=0; col<squareCols; col++) {							
				Square block = new Square( row, col );
				block.setSize(squareWidth - 0.0075f, squareHeight - 0.0075f); //Make a nice black border with the -0.0075
				block.setOrigin(0, 0);
				block.setPosition(-0.5f + col*squareWidth, h/(w*2) - (row+1)*squareHeight); //Viewport goes from -0.5f to 0.5f
				
				changeColor(block);
				
				square.add(block);
				
				block.appear((float) (Math.random()*6), manager);
				
			}
		}
		
		sequencer.gridCreated(square);
	}
	
	private void destroyGrid(int newRows, int newCols) {
		int animationType = (int) Math.floor(Math.random()*Square.animNum);
		float animationTime = 1.4f;
		
		for (int i=0; i<square.size(); i++) {
			square.get(i).destroy(manager, animationType, animationTime);
		}
		
		Tween.call(new ReplaceGridCallback(newRows, newCols, this))
			.delay(animationTime)
			.start(manager);
	}
	
	public int changeColor(Square block, int colorNum) {
		TextureRegion region = new TextureRegion(texBlue, 0, 0, 1, 1); //Initialize
		
		switch(colorNum) {
			case Square.colorBlue: region = new TextureRegion(texBlue, 0, 0, 1, 1); break;
			case Square.colorGreen: region = new TextureRegion(texGreen, 0, 0, 1, 1); break;
			case Square.colorOrange: region = new TextureRegion(texOrange, 0, 0, 1, 1); break;
			case Square.colorPurple: region = new TextureRegion(texPurple, 0, 0, 1, 1); break;
			default: new TextureRegion(four, 0, 0, 16, 16); break;
		}
		
		block.setRegion(region);
		block.setSquareColor(colorNum);
		
		return colorNum;
	}
	
	public int changeColor(Square block) {
		int colorNum = (int) Math.floor(Math.random()*numOfPlayers);
		return changeColor(block, colorNum);
	}
	
	private void addMenuItem(Texture squareTexture, float x, float y, float size, float delayTime, float animTime) {
		addMenuItem(squareTexture, x, y, size, delayTime, animTime, 0, 0, 16, 16);
	}
	
	private void addMenuItem(Texture squareTexture, float x, float y, float size, float delayTime, float animTime, int texX, int texY, int texHeight, int texWidth) {
		TextureRegion region = new TextureRegion(squareTexture, texX, texY, texHeight, texWidth);
		
		Sprite introSquare = new Sprite(region);
		
		introSquare.setSize(size, size);
		introSquare.setOrigin(0, 0);
		introSquare.setPosition(x,0.8f); //Viewport goes from -0.5f to 0.5f
		
		Tween.to(introSquare, SpriteAccessor.POSITION_XY, animTime)
			.target(x, y)
			.ease(Back.OUT)
			.delay(delayTime)
			.start(manager);
		
		displayList.add(introSquare);
	}
	
	public void intro() {
		gameStep = 0;
		gameTimeSeconds = 0;
		gameState = INTRO_MENU;
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		displayList.clear();
		
		addMenuItem(help_bg, -0.5f, -0.5f, 1.0f, 0, 0, 0, 0, 1024, 1024);
		
		addMenuItem(question,  -0.03f, -h/w/2 + 0.01f, 0.06f, 3.4f, 0.8f);
		
		addMenuItem(two,  -0.19f, -0.10f, 0.1f, 2.8f, 0.5f); //This must be the third square
		addMenuItem(three,-0.05f, -0.10f, 0.1f, 2.8f, 0.5f); //This must be the fourth square
		addMenuItem(four,  0.09f, -0.10f, 0.1f, 2.8f, 0.5f); //This must be the fifth square
		
		addMenuItem(S, -0.265f, h/w/2 - 0.12f, 0.08f, 0.2f, 0.5f);
		addMenuItem(Q, -0.175f, h/w/2 - 0.12f, 0.08f, 0.4f, 0.5f);
		addMenuItem(U, -0.085f, h/w/2 - 0.12f, 0.08f, 0.6f, 0.5f);
		addMenuItem(A,  0.005f, h/w/2 - 0.12f, 0.08f, 0.8f, 0.5f);
		addMenuItem(R,  0.095f, h/w/2 - 0.12f, 0.08f, 1.0f, 0.5f);
		addMenuItem(E,  0.185f, h/w/2 - 0.12f, 0.08f, 1.2f, 0.5f);
		
		addMenuItem(J, 		-0.13f, h/w/2 - 0.22f, 0.08f, 1.8f, 0.5f);
		addMenuItem(JAM_A, 	-0.04f, h/w/2 - 0.22f, 0.08f, 2.0f, 0.5f);
		addMenuItem(M, 		 0.05f, h/w/2 - 0.22f, 0.08f, 2.2f, 0.5f);
		
		TextureRegion region = new TextureRegion(twitt, 0, 0, 143, 13);
		
		Sprite twittSprite = new Sprite(region);
		
		twittSprite.setSize(0.5f, 13.0f/143.0f/2.0f);
		twittSprite.setOrigin(twittSprite.getWidth()/2, twittSprite.getHeight()/2);
		twittSprite.setPosition(-0.25f, -h/w/2 + 0.1f);
		
		Tween.set(twittSprite, SpriteAccessor.OPACITY)
			.target(0)
			.start(manager);
		
		Tween.to(twittSprite, SpriteAccessor.OPACITY, 3.0f)
			.target(1.0f)
			.delay(4.3f)
			.start(manager);
		
		displayList.add(twittSprite);
	}
	
	public void help() {
		gameState = HELP_MENU;
		displayList.clear();
		
		HelpAnimation.start(displayList, manager, dark_hand, light_hand, medium_hand, tan_hand, texBlue, texGreen, texPurple, texOrange, tablet, help_bg);
	}
	
	public void tweenRectYSize(Sprite block, float y, float animTime, float delay) {
		Tween.to(block, SpriteAccessor.SIZE_Y, animTime)
		.target(y)
		.delay(delay)
		.start(manager);
	}
	
	public void unPauseScoreScreen() {
		scoreScreenPaused = false;
	}
	
	public void endGame() {
		gameStep = 0;
		gameState = SCORE_SCREEN;
		
		scoreScreenPaused = true;
		
		destroyGrid(0, 0);
		
		System.out.println("Orange: "	+orangeScore);
		System.out.println("Blue: "		+blueScore);
		System.out.println("Green: "	+greenScore);
		System.out.println("Purple: "	+purpleScore);
		
		System.out.println("DONE!");
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		float maxScore = Math.max( Math.max(purpleScore, blueScore), Math.max(greenScore, orangeScore) );
		
		if (maxScore == 0) { //Prevent divide by 0
			maxScore = 1;
		}
		
		Sprite purpleScoreRect = new Sprite(	new TextureRegion(texPurple, 0, 0, 1, 1)   );
		purpleScoreRect.setSize(0.25f, 1);
		purpleScoreRect.setOrigin(0, 0);
		purpleScoreRect.setPosition(0.25f,-h/(2*w));
		
		Sprite blueScoreRect = new Sprite(		new TextureRegion(texBlue, 0, 0, 1, 1)   );
		blueScoreRect.setSize(0.25f, 1);
		blueScoreRect.setOrigin(0, 0);
		blueScoreRect.setPosition(-0.25f,-h/(2*w));
		
		Sprite greenScoreRect = new Sprite(		new TextureRegion(texGreen, 0, 0, 1, 1)   );
		greenScoreRect.setSize(0.25f, 1);
		greenScoreRect.setOrigin(0, 0);
		greenScoreRect.setPosition(-0.0f,-h/(2*w));
		
		Sprite orangeScoreRect = new Sprite(	new TextureRegion(texOrange, 0, 0, 1, 1)   );
		orangeScoreRect.setSize(0.25f, 1);
		orangeScoreRect.setOrigin(0, 0);
		orangeScoreRect.setPosition(-0.5f,-h/(2*w));
				
		tweenRectYSize(purpleScoreRect, 0, 0, 0);
		tweenRectYSize(blueScoreRect, 0, 0, 0);
		tweenRectYSize(greenScoreRect, 0, 0, 0);
		tweenRectYSize(orangeScoreRect, 0, 0, 0);
		
		tweenRectYSize(orangeScoreRect, orangeScore*(h/w)/maxScore, 	1.0f*orangeScore*(h/w)/maxScore + 0.5f, 	0);
		tweenRectYSize(blueScoreRect, 	blueScore*(h/w)/maxScore, 		1.0f*blueScore*(h/w)/maxScore   + 0.5f,		1.7f);
		tweenRectYSize(greenScoreRect, 	greenScore*(h/w)/maxScore, 		1.0f*greenScore*(h/w)/maxScore  + 0.5f, 	3.4f);
		tweenRectYSize(purpleScoreRect, purpleScore*(h/w)/maxScore, 	1.0f*purpleScore*(h/w)/maxScore + 0.5f, 	5.1f);	
		
		Tween.call(new ScoreScreenCallback(this))
			.delay(5.0f)
			.start(manager);
		
		displayList.add(purpleScoreRect);
		displayList.add(blueScoreRect);
		displayList.add(greenScoreRect);
		displayList.add(orangeScoreRect);
		
		purpleScore = 0;
		blueScore = 0;
		greenScore = 0;
		orangeScore = 0;
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
