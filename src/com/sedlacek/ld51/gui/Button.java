package com.sedlacek.ld51.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sedlacek.ld51.main.AudioPlayer;
import com.sedlacek.ld51.main.Config;
import com.sedlacek.ld51.main.Game;

public class Button extends GUI{

    public String[] text;
    public Color color;
    public Color textColor;
    public Font textFont = new Font("DorFont03", Font.BOLD, 12*Config.SIZE_MULT);

    public int border_size = 2;
    public boolean active = false;
    public boolean disabled = false;
    public boolean disableOnPause = false;
    public BufferedImage icon;
    public BufferedImage disabledIcon;
    public int multiplier;
    public int yoffsetText = 0;
    public int xoffsetText = 0;

    public static final AudioPlayer sound = new AudioPlayer("/button1.wav");

    public Button() {
    	
    }
    
    public Button(String[] text, int x, int y, int w, int h, Color color, Color textColor, Font f, Method m, Object invoker) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        this.textColor = textColor;
        this.clickedMethod = m;
        this.invoker = invoker;
        this.multiplier = 1;
        this.textFont = f;
    }

    @Override
    public void update() {
        super.update();
        if(disableOnPause){
            if(Game.paused) {
                this.disabled = true;
            }else{
                this.disabled = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if(disabled) {
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
            g.fillRect(x, y, w, h);
            g.setColor(new Color(color.brighter().getRed(),color.brighter().getGreen(), color.brighter().getBlue(), 100));
            g.fillRect(x+border_size, y+border_size, w-border_size*2, h-border_size*2);
        }else if(this.mouseOver || this.active) {
            g.setColor(color);
            g.fillRect(x, y, w, h);
            g.setColor(color.brighter());
            g.fillRect(x+border_size, y+border_size, w-border_size*2, h-border_size*2);
        }
        else{
            g.setColor(color.darker());
            g.fillRect(x, y, w, h);
            g.setColor(color);
            g.fillRect(x+border_size, y+border_size, w-border_size*2, h-border_size*2);
        }
        if(!disabled) {
            g.setColor(textColor);
        }
        else{
            g.setColor(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 100));
        }

        if(this.icon != null){
            if(this.disabledIcon != null && disabled){
                g.drawImage(this.disabledIcon,
                        x + w / 2 - disabledIcon.getWidth() * multiplier / 2, y + h / 2 - disabledIcon.getHeight() * multiplier / 2,
                        this.disabledIcon.getWidth() * this.multiplier,
                        disabledIcon.getHeight() * this.multiplier, null);
            }else {
                g.drawImage(this.icon,
                        x + w / 2 - icon.getWidth() * multiplier / 2, y + h / 2 - icon.getHeight() * multiplier / 2,
                        this.icon.getWidth() * this.multiplier,
                        icon.getHeight() * this.multiplier, null);
            }
        }

        g.setFont(textFont);
        int space = g.getFontMetrics().getMaxAscent()+1*Config.SIZE_MULT;
        int off = 0;
        for(String s: text) {
	        g.drawString(s, x+w/2-g.getFontMetrics().stringWidth(s)/2+xoffsetText,
	                y+off+h-g.getFontMetrics().getMaxAscent()/2+yoffsetText);
	        off += space;
        }
    }

    void setText(String[] text){
        this.text = text;
    }

    @Override
    public void clicked() {
        if(clickedMethod != null && !disabled){
        	if(Game.soundOn)
        		sound.playClip();
            try {
                clickedMethod.invoke(this.invoker);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public void setDisabledIcon(BufferedImage disabledIcon) {
        this.disabledIcon = disabledIcon;
    }

	public void setYOffset(int i) {
		this.yoffsetText = i;
		
	}
}
