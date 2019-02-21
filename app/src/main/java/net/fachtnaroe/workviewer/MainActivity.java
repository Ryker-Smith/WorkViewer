package net.fachtnaroe.workviewer;

// you need the following imports in all Java Bridge apps
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.VerticalArrangement;

public class MainActivity extends Form implements HandlesEventDispatching
{

    private Button buttonRefresh;
    private VerticalArrangement vargParent;
    private HorizontalArrangement hargTop;

    protected void $define()
    {
        vargParent =new VerticalArrangement(this);
        vargParent.WidthPercent(50);
        vargParent.HeightPercent(100);
        vargParent.BackgroundColor(COLOR_BLACK );
        hargTop = new HorizontalArrangement(vargParent);
        hargTop.WidthPercent(100);
        hargTop.Height(50);
        buttonRefresh = new Button(hargTop);
        buttonRefresh.Text( "Refresh" );
        buttonRefresh.Height(50);

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
        // here is where you'd check for other events of your app...
        return false;
    }
    // this is the event handler that dispatchEvent above calls. We just set the button background
    public void RefreshButton_Click()
    {

    }

}