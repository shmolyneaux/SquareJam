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
import aurelienribon.tweenengine.equations.Cubic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HelpAnimation {
	public static void start(List<Sprite> displayList, TweenManager manager, Texture dark_hand, 
								Texture light_hand, Texture medium_hand, Texture tan_hand, Texture texBlue,
								Texture texGreen, Texture texPurple, Texture texOrange, Texture tablet,
								Texture help_bg) {
		final float squareApparitionDelay = 0.3f;
		final float squareApparitionTime = 1.3f;
		final float squareDelayBetweenApparitions = 0.5f;
		
		final float squareSlideDelay = squareApparitionDelay + squareDelayBetweenApparitions*3 + squareApparitionTime + 0.8f;
		final float squareSlideTime = 2.2f;
		final float squareDelayBetweenSlides = 0.7f;

		final float tabletApparitionDelay = squareSlideDelay + squareDelayBetweenSlides*3 + squareSlideTime + -0.3f;
		final float tabletApparitionTime = 1.1f;
		
		final float handApparitionDelay = tabletApparitionDelay + tabletApparitionTime + 0.3f;
		//TODO list other hand timings
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		//TODO condense into methods
		
		TextureRegion region = new TextureRegion(help_bg, 0, 0, 1024, 1024);
		
		Sprite bgSprite = new Sprite(region);
		
		bgSprite.setSize(1, 1);
		bgSprite.setOrigin(bgSprite.getWidth()/2.0f, bgSprite.getHeight()/2.0f);
		bgSprite.setPosition(-0.5f, -0.5f);

		displayList.add(bgSprite);
		
		//Add purple square and animate
		region = new TextureRegion(texPurple, 0, 0, 1, 1);
		
		Sprite purpleSprite = new Sprite(region);
		
		purpleSprite.setSize(0.15f, 0.15f);
		purpleSprite.setOrigin(purpleSprite.getWidth()/2.0f, purpleSprite.getHeight()/2.0f);
		purpleSprite.setPosition(-0.075f,0.1f);
		
		displayList.add(purpleSprite);
		
		Tween.set(purpleSprite, SpriteAccessor.SIZE_XY)
			.target(0,0)
			.start(manager);
		
		Tween.to(purpleSprite, SpriteAccessor.SIZE_XY, squareApparitionTime)
			.target(1,1)
			.delay(squareApparitionDelay + squareDelayBetweenApparitions*0)
			.start(manager);
		
		//Add blue square and animate
		region = new TextureRegion(texBlue, 0, 0, 1, 1);
		
		Sprite blueSprite = new Sprite(region);
		
		blueSprite.setSize(0.15f, 0.15f);
		blueSprite.setOrigin(blueSprite.getWidth()/2.0f, blueSprite.getHeight()/2.0f);
		blueSprite.setPosition(-0.25f,-0.075f);
		
		displayList.add(blueSprite);
		
		Tween.set(blueSprite, SpriteAccessor.SIZE_XY)
			.target(0,0)
			.start(manager);
		
		Tween.to(blueSprite, SpriteAccessor.SIZE_XY, squareApparitionTime)
			.target(1,1)
			.delay(squareApparitionDelay + squareDelayBetweenApparitions*1)
			.start(manager);
		
		//Add orange square and animate
		region = new TextureRegion(texOrange, 0, 0, 1, 1);
		
		Sprite orangeSprite = new Sprite(region);
		
		orangeSprite.setSize(0.15f, 0.15f);
		orangeSprite.setOrigin(orangeSprite.getWidth()/2.0f, orangeSprite.getHeight()/2.0f);
		orangeSprite.setPosition(0.1f,-0.075f);
		
		displayList.add(orangeSprite);
		
		Tween.set(orangeSprite, SpriteAccessor.SIZE_XY)
			.target(0,0)
			.start(manager);
		
		Tween.to(orangeSprite, SpriteAccessor.SIZE_XY, squareApparitionTime)
			.target(1,1)
			.delay(squareApparitionDelay + squareDelayBetweenApparitions*2)
			.start(manager);
				
		//Add green square and animate
		region = new TextureRegion(texGreen, 0, 0, 1, 1);
		
		Sprite greenSprite = new Sprite(region);
		
		greenSprite.setSize(0.15f, 0.15f);
		greenSprite.setOrigin(greenSprite.getWidth()/2.0f, greenSprite.getHeight()/2.0f);
		greenSprite.setPosition(-0.075f,-0.25f);
		
		displayList.add(greenSprite);
		
		Tween.set(greenSprite, SpriteAccessor.SIZE_XY)
			.target(0,0)
			.start(manager);
		
		Tween.to(greenSprite, SpriteAccessor.SIZE_XY, squareApparitionTime)
			.target(1,1)
			.delay(squareApparitionDelay + squareDelayBetweenApparitions*3)
			.start(manager);
		
		//Slides
		Tween.to(purpleSprite, SpriteAccessor.POSITION_Y, squareSlideTime)
			.target(1.1f)
			.delay(squareSlideDelay + squareDelayBetweenSlides*0)
			.start(manager);
		
		Tween.to(blueSprite, SpriteAccessor.POSITION_X, squareSlideTime)
			.target(-1.25f)
			.delay(squareSlideDelay + squareDelayBetweenSlides*1)
			.start(manager);
		
		Tween.to(orangeSprite, SpriteAccessor.POSITION_X, squareSlideTime)
			.target(1.1f)
			.delay(squareSlideDelay + squareDelayBetweenSlides*2)
			.start(manager);
		
		Tween.to(greenSprite, SpriteAccessor.POSITION_Y, squareSlideTime)
			.target(-1.25f)
			.delay(squareSlideDelay + squareDelayBetweenSlides*3)
			.start(manager);
		
		//Add Tablet and animate 
		region = new TextureRegion(tablet, 0, 0, 512, 386);
		
		Sprite tabSprite = new Sprite(region);
		
		tabSprite.setSize(0.5f, 386.0f/512.0f/2.0f);
		tabSprite.setOrigin(tabSprite.getWidth()/2, tabSprite.getHeight()/2);
		tabSprite.setPosition(-0.25f, h/w/2);
		
		displayList.add(tabSprite);
		
		Tween.to(tabSprite, SpriteAccessor.POSITION_XY, tabletApparitionTime)
			.target(-0.25f,-0.2f)
			.ease(Cubic.INOUT)
			.delay(tabletApparitionDelay)
			.start(manager);
		
		//Add dark hand and animate
		region = new TextureRegion(dark_hand, 120, 0, 256, 256);
		
		Sprite dhSprite = new Sprite(region);
		
		dhSprite.setSize(0.25f, 0.25f);
		dhSprite.setOrigin(0.09f, 0.23f);
		dhSprite.setPosition(0,0.3f);
		dhSprite.setRotation(180);
		
		displayList.add(dhSprite);
		
		Tween.to(dhSprite, SpriteAccessor.POSITION_XY, 1.3f)
			.target(0.07f,-0.13f)
			.delay(handApparitionDelay + 0.2f)
			.start(manager);
		
		Tween.to(dhSprite, SpriteAccessor.POSITION_XY, 1.3f)
			.target(-0.03f,-0.33f)
			.delay(handApparitionDelay + 2.3f)
			.start(manager);
		
		Tween.to(dhSprite, SpriteAccessor.POSITION_XY, 1.0f)
			.target(0.07f,-0.33f)
			.delay(handApparitionDelay + 4.4f)
			.start(manager);
		
		Tween.to(dhSprite, SpriteAccessor.POSITION_XY, 2.0f)
			.target(-0.07f, 0.03f)
			.delay(handApparitionDelay + 6.5f)
			.start(manager);
		
		//Add light hand and animate
		region = new TextureRegion(light_hand, 120, 0, 256, 256);
		
		Sprite lhSprite = new Sprite(region);
		
		lhSprite.setSize(0.25f, 0.25f);
		lhSprite.setOrigin(0.09f, 0.23f);
		lhSprite.setPosition(-0.7f,0.0f);
		lhSprite.setRotation(270);
		
		displayList.add(lhSprite);
		
		Tween.to(lhSprite, SpriteAccessor.POSITION_XY, 2.3f)
			.target(-0.03f,-0.13f)
			.delay(handApparitionDelay)
			.start(manager);
		
		Tween.to(lhSprite, SpriteAccessor.POSITION_XY, 1.3f)
			.target(-0.25f,-0.23f)
			.delay(handApparitionDelay + 3.1f)
			.start(manager);
		
		Tween.to(lhSprite, SpriteAccessor.POSITION_XY, 1.0f)
			.target(-0.5f,-0.25f)
			.delay(handApparitionDelay + 5.2f)
			.start(manager);
		
		//Add tan hand and animate
		region = new TextureRegion(tan_hand, 120, 0, 256, 256);
		
		Sprite thSprite = new Sprite(region);
		
		thSprite.setSize(0.25f, 0.25f);
		thSprite.setOrigin(0.09f, 0.23f);
		thSprite.setPosition(0.5f,-0.2f);
		thSprite.setRotation(90);
		
		displayList.add(thSprite);
		
		Tween.to(thSprite, SpriteAccessor.POSITION_XY, 2.0f)
			.target(-0.03f,-0.23f)
			.delay(handApparitionDelay + 0.1f)
			.start(manager);
		
		Tween.to(thSprite, SpriteAccessor.POSITION_XY, 1.2f)
			.target(-0.13f,-0.25f)
			.delay(handApparitionDelay + 2.5f)
			.start(manager);
		
		Tween.to(thSprite, SpriteAccessor.POSITION_XY, 1.2f)
			.target(-0.13f,-0.14f)
			.delay(handApparitionDelay + 3.8f)
			.start(manager);
		
		Tween.to(thSprite, SpriteAccessor.POSITION_XY, 1.5f)
			.target(-0.23f,-0.35f)
			.delay(handApparitionDelay + 6.0f)
			.start(manager);
		
		Tween.to(thSprite, SpriteAccessor.POSITION_XY, 2.0f)
			.target(0.3f,-0.2f)
			.delay(handApparitionDelay + 8.1f)
			.start(manager);
		
		//Add medium hand and animate
		region = new TextureRegion(medium_hand, 120, 0, 256, 256);
		
		Sprite mhSprite = new Sprite(region);
		
		mhSprite.setSize(0.25f, 0.25f);
		mhSprite.setOrigin(0.09f, 0.23f);
		mhSprite.setPosition(0.0f,-0.6f);
		
		displayList.add(mhSprite);
		
		Tween.to(mhSprite, SpriteAccessor.POSITION_XY, 1.8f)
			.target(-0.13f,-0.33f)
			.delay(handApparitionDelay + 0.4f)
			.start(manager);
		
		Tween.to(mhSprite, SpriteAccessor.POSITION_XY, 1.4f)
			.target(0.07f,-0.23f)
			.delay(handApparitionDelay + 3.3f)
			.start(manager);
		
		Tween.to(mhSprite, SpriteAccessor.POSITION_XY, 2.0f)
			.target(-0.25f,-0.13f)
			.delay(handApparitionDelay + 5.5f)
			.start(manager);
		
		Tween.to(mhSprite, SpriteAccessor.POSITION_XY, 2.0f)
			.target(0.0f,-0.5f)
			.delay(handApparitionDelay + 8.1f)
			.start(manager);
	}
}
