/* Copyright © 2012 Stephen Molyneaux <@SHMolyneaux>
 * 
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */

package com.example.squarejam;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Square extends Sprite {
	public boolean touched = false;
	public boolean beingDestroyed = false;
	public boolean appearing = false;
	
	public static final int colorGreen = 0;
	public static final int colorBlue = 1;
	public static final int colorOrange = 2;
	public static final int colorPurple = 3;
	
	public static final int animNum = 4;
	public static final int animShrink = 0;
	public static final int animSlide = 1;
	public static final int animSplatter = 2;
	public static final int animExplode = 3;
	
	private int squareColor = 0;
	private int squareRow;
	private int squareCol;
	
	private float animationTime = 0.7f;
	private float minResetTime = 2.0f;
	private float maxAdditionalResetTime = 4.0f;
	
	public Square(TextureRegion region, int sr, int sc) {
		super(region);
		
		setSquareRow(sr);
		setSquareCol(sc);
	}
	
	public Square(int sr, int sc) {
		super();
		
		setSquareRow(sr);
		setSquareCol(sc);
	}
	
	public int getSquareColor() {
		return squareColor;
	}

	public void setSquareColor(int color) {
		if (color < 4 && color >= 0) {
			this.squareColor = color;
		}
	}
	
	public void appear(float timeDelay, TweenManager manager) {
		appearing = true;
		
		Tween.to(this, SpriteAccessor.SIZE_XY, animationTime)
			.target(this.getScaleX(), this.getScaleX())
			.ease(Back.OUT)
			.delay(timeDelay)
			.start(manager);
		
		Tween.to(this, SpriteAccessor.POSITION_XY, animationTime)
			.target(this.getX(), this.getY())
			.ease(Back.OUT)
			.delay(timeDelay)
			.start(manager);
		
		Tween.call(new ApparitionCallback(this))
			.delay(timeDelay + animationTime)
			.start(manager);
		
		setPosition(this.getX() + this.getWidth()*0.5f, this.getY() + this.getHeight()*0.5f);
		setScale(0);
	}
	
	public boolean touch(MyGdxGame myGame, TweenManager manager) {
		if (touched || beingDestroyed || appearing) {
			return false;
		}
		
		touched = true;
		
		float resetTime = (float) (minResetTime + Math.random()*maxAdditionalResetTime);
		
		Tween.to(this, SpriteAccessor.SIZE_XY, animationTime)
			.target(0.0f, 0.0f)
			.start(manager);
		
		Tween.to(this, SpriteAccessor.POSITION_XY, animationTime)
			.target(this.getX() + this.getWidth()*0.5f, this.getY() + this.getHeight()*0.5f)
			.start(manager);
		
		Tween.to(this, SpriteAccessor.SIZE_XY, animationTime)
			.target(this.getScaleX(), this.getScaleX())
			.ease(Back.OUT)
			.delay(resetTime)
			.start(manager);
		
		Tween.to(this, SpriteAccessor.POSITION_XY, animationTime)
			.target(this.getX(), this.getY())
			.ease(Back.OUT)
			.delay(resetTime)
			.start(manager);
		
		/*Tween.call(new SquareColorChangeCallback(this, myGame))	Color change applied in sequencer
			.delay(animationTime)
			.start(manager);*/
		
		Tween.call(new SquareTouchCallback(this, myGame))
			.delay(resetTime + animationTime)
			.start(manager);
		
		return true;
	}
	
	public void destroy(TweenManager manager, int animNum, float animSpeed) {
		beingDestroyed = true;
		
		Tween.to(this, SpriteAccessor.SIZE_XY, animSpeed)
			.target(0.0f, 0.0f)
			.start(manager);

		float x = 0;
		float y = 0;
		
		switch (animNum) {
			case animSlide:
				if (Math.random() > 0.5f) {
					if (getX() > 0) {
						x = -1.0f;
					}
					
					else {
						x = 1.0f;
					}
					
					y = 0;
				}
				
				else {
					if (getY() > 0) {
						y = -1.0f;
					}
					
					else {
						y = 1.0f;
					}
					
					x = 0;
				}
				break;
			
			case animSplatter:
				x = (float) (Math.random()-0.5f);
				y = (float) (Math.random()-0.5f);
				break;
				
			case animExplode:
				x = getX()*-4;
				y = getY()*-4;
				break;
				
			case animShrink:
				x = 0;
				y = 0;
				break;
				
			default:
				x = 0;
				y = 0;
				break;
		}
		
		Tween.to(this, SpriteAccessor.POSITION_XY, animSpeed)
			.target(x + this.getX() + this.getWidth()*0.5f, y + this.getY() + this.getHeight()*0.5f)
			.start(manager);
		
	}

	public void setTiming(float anim, float resetTime, float addResetTime) {
		animationTime = anim;
		minResetTime = resetTime;
		maxAdditionalResetTime = addResetTime;
	}
	
	public float getAnimationTime() {
		return animationTime;
	}
	
	public int getSquareRow() {
		return squareRow;
	}

	public void setSquareRow(int squareRow) {
		this.squareRow = squareRow;
	}

	public int getSquareCol() {
		return squareCol;
	}

	public void setSquareCol(int squareCol) {
		this.squareCol = squareCol;
	}
}
