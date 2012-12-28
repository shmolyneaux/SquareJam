/* Copyright © 2012 Stephen Molyneaux <@SHMolyneaux>
 * 
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */

package com.example.squarejam;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite> {

	public static final int SIZE_X = 1;
	public static final int SIZE_Y = 2;
	public static final int SIZE_XY = 3;
	
	public static final int POSITION_X = 4;
	public static final int POSITION_Y = 5;
	public static final int POSITION_XY = 6;
	
	public static final int ROTATION = 7;
	
	public static final int OPACITY = 8;
	
	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
	        case SIZE_X: returnValues[0] = target.getScaleX(); return 1;
	        case SIZE_Y: returnValues[0] = target.getScaleY(); return 1;
	        case SIZE_XY:
	            returnValues[0] = target.getScaleX();
	            returnValues[1] = target.getScaleY();
	            return 2;
	            
	        case POSITION_X: returnValues[0] = target.getX(); return 1;
	        case POSITION_Y: returnValues[0] = target.getY(); return 1;
	        case POSITION_XY: 
	        	returnValues[0] = target.getX();
	        	returnValues[1] = target.getY();
	        	return 2;
	        	
	        case ROTATION: returnValues[0] = target.getRotation(); return 1;
	        case OPACITY: returnValues[0] = target.getColor().a; return 1;
	        	
	        default: assert false; return -1;
	    }
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
	        case SIZE_X: target.setScale(newValues[0], target.getScaleY()); break;
	        case SIZE_Y: target.setScale(target.getScaleX(), newValues[0]); break;
	        case SIZE_XY:
	            target.setScale(newValues[0], newValues[1]);
	            break;
	            
	        case POSITION_X: target.setPosition(newValues[0], target.getY()); break;
	        case POSITION_Y: target.setPosition(target.getX(), newValues[0]); break;
	        case POSITION_XY: 
	        	target.setPosition(newValues[0], newValues[1]);
	            break;
	            
	        case ROTATION: target.setRotation(newValues[0]); break;
	        case OPACITY: target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]); break;
	            
	        default: assert false; break;
	    }
	}

}
