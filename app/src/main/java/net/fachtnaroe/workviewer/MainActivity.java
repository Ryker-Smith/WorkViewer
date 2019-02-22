package net.fachtnaroe.workviewer;

// you need the following imports in all Java Bridge apps
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.WebViewer;

public class MainActivity extends Form implements HandlesEventDispatching
{

    Button buttonRefresh;
    VerticalArrangement vargParent;
    HorizontalArrangement hargTop;
    WebViewer webviewMainDisplay;
    Label padOne, padTwo;
    String home_url="http://fachtnaroe.net:4567";

    protected void $define()
    {
        vargParent =new VerticalArrangement(this);
        vargParent.WidthPercent(100);
        vargParent.HeightPercent(100);
        vargParent.BackgroundColor(COLOR_BLACK );
        hargTop = new HorizontalArrangement(vargParent);
        hargTop.AlignHorizontal(Component.ALIGNMENT_CENTER);
        hargTop.WidthPercent(100);
        hargTop.Height(50);
        padOne = new Label(hargTop);
        buttonRefresh = new Button(hargTop);
        buttonRefresh.Image("buttonRefresh.png");
        buttonRefresh.Width(50);
        buttonRefresh.Height(50);
        padTwo=new Label(hargTop);
        padOne.WidthPercent(40);
        padTwo.WidthPercent(40);
        webviewMainDisplay = new WebViewer(vargParent);
        webviewMainDisplay.ClearCaches();
        webviewMainDisplay.FollowLinks(true);
        webviewMainDisplay.HomeUrl(home_url);
        webviewMainDisplay.HeightPercent(100);
        webviewMainDisplay.WidthPercent(100);
        webviewMainDisplay.IgnoreSslErrors(true);
        webviewMainDisplay.GoHome();

        EventDispatcher.registerEventForDelegation( this, formName, "Click" );
        EventDispatcher.registerEventForDelegation( this, formName, "GotText" );
        EventDispatcher.registerEventForDelegation( this, formName, "WebStringChange" );
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params )
    {
        if( eventName.equals("Click") ) {
            if (component.equals(buttonRefresh)) {
                RefreshButton_Click();
                return true;
            }
        }
        return false;
    }

    public void RefreshButton_Click() {
        webviewMainDisplay.GoHome();
    }

}