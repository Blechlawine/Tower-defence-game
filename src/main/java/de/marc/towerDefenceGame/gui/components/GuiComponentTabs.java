package de.marc.towerDefenceGame.gui.components;

import de.marc.towerDefenceGame.TowerDefenceGame;
import de.marc.towerDefenceGame.event.Event;
import de.marc.towerDefenceGame.event.events.KeyEvent;
import de.marc.towerDefenceGame.gui.GuiComponent;
import de.marc.towerDefenceGame.gui.GuiInteractableComponent;
import de.marc.towerDefenceGame.gui.GuiLayoutComponent;
import de.marc.towerDefenceGame.gui.GuiScreen;
import de.marc.towerDefenceGame.utils.*;

import java.util.ArrayList;

import static de.marc.towerDefenceGame.utils.KeyAction.DOWN;
import static de.marc.towerDefenceGame.utils.Settings.KeyBindings.GUI_INTERACT;

public class GuiComponentTabs extends GuiInteractableComponent {

    private String[] values;
    private GuiLayoutComponent[] tabContentComponents;
    private ArrayList<GuiComponentTabContent> tabs = new ArrayList<>();
    private String activeTab;
    private double tabHeight;

    private Keybinding changeTabBinding;
    private int hoveredValueIndex = 0;

    public GuiComponentTabs(Vector2 relativePos,
                            GuiComponent parent,
                            double width,
                            double height,
                            String[] values,
                            double tabHeight,
                            int defaultIndex,
                            ArrayList<GuiComponentTabContent> tabs,
                            double tabContentHeight
    ) {
        super(relativePos, parent, width, tabHeight * values.length);
        this.values = values;
        this.tabHeight = tabHeight;
        this.tabs = tabs;
        for(GuiComponentTabContent tab : this.tabs) {
            tab.setParent(this);
        }
        this.activeTab = this.values[defaultIndex];
        this.tabs.get(defaultIndex).setVisible(true);
        this.changeTabBinding = new Keybinding(GUI_INTERACT, new KeyAction[]{DOWN}) {
            @Override
            public void onKeyAction(KeyAction action, KeyEvent event) {
                if (hovered) {
                    activeTab = values[hoveredValueIndex];
                    for(int i = 0; i < tabs.size(); i++) {
                        GuiComponentTabContent tab = tabs.get(i);
                        tab.setVisible(i == hoveredValueIndex);
                    }
                }
            }
        };
    }

    @Override
    protected void onMouseOver(double relativeMouseX, double relativeMouseY) {
        this.hoveredValueIndex = (int)Math.round(relativeMouseY) / (int)this.tabHeight;
    }
    @Override
    protected void onMouseOut() {
        this.hoveredValueIndex = -1;
    }

    @Override
    public void render() {
        double padding = (this.tabHeight - TowerDefenceGame.theGame.getFontRenderer().getCharHeight(2)) / 2;
        double pressedTextOffset = this.tabHeight / 16 * 3;
        for(int i = 0; i < this.values.length; i++) {
            String value = this.values[i];
            Vector2 valuePos = new Vector2(this.getAbsolutePos()).add(new Vector2(0, (this.tabHeight + 4) * i));

            String textureHandle = (this.activeTab.equals(value) ? "buttonpressed" : "button");
            // left side
            GLUtils.drawTexturedRect(valuePos.getX(), valuePos.getY(), this.tabHeight, this.tabHeight, 0, 0, 1, 1, textureHandle, new Color(1, 1, 1));
            // right side
            GLUtils.drawTexturedRect(valuePos.getX() + this.width - this.tabHeight, valuePos.getY(), this.tabHeight, this.tabHeight, 1, 0, -1, 1, textureHandle, new Color(1, 1, 1));
            // middle part
            GLUtils.drawTexturedRect(valuePos.getX() + this.tabHeight, valuePos.getY(), this.width - (this.tabHeight*2), this.tabHeight, 0.5, 0, 0.5, 1, textureHandle, new Color(1, 1, 1));

//            GLUtils.drawRect(valuePos.getX(), valuePos.getY(), this.width, this.tabHeight, (this.hoveredValueIndex == i || this.activeTab.equals(value) ? this.hoverColor : this.initialColor));
            TowerDefenceGame.theGame.getFontRenderer().drawString(value, new Vector2(valuePos).add(new Vector2(padding, padding + (this.activeTab.equals(value) ? pressedTextOffset / 2 : -pressedTextOffset / 2))), 2, new Color(Colors.TEXT));
        }
        for(GuiComponentTabContent tabContent : this.tabs) {
            tabContent.render();
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        this.changeTabBinding.onEvent(event);
        for (GuiComponentTabContent tabContent : this.tabs) {
            tabContent.onEvent(event);
        }
    }
}
