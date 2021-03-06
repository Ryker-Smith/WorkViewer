package net.fachtnaroe.workviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.VerticalArrangement;

// you need the following imports in all Java Bridge apps
import android.content.Intent;
import android.net.Uri;

import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;

import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.VerticalArrangement;

public class Calendar extends Form implements HandlesEventDispatching {

        private Button buttonRefresh, buttonReturn;
        private VerticalArrangement vargParent;
        private HorizontalArrangement hargTop;
        private fachtnaWebViewer webviewMainDisplay;
        private Label debugLabel, padTwo;
        private String ordering="calendar/";
        private String home_url="http://fachtnaroe.net:4567/" + ordering;
        private  String base_url="briefs/";
        String lastWebViewString="";
        Notifier messages;
        private Integer colour_bad= Component.COLOR_RED;
        private Integer colour_good=Component.COLOR_DKGRAY;

        protected void $define() {
            vargParent =new VerticalArrangement(this);
            vargParent.WidthPercent(100);
            vargParent.HeightPercent(100);
            vargParent.BackgroundColor(COLOR_BLACK );
            hargTop = new HorizontalArrangement(vargParent);
            hargTop.AlignHorizontal(Component.ALIGNMENT_CENTER);
            hargTop.WidthPercent(100);
            hargTop.Height(50);
            debugLabel = new Label(hargTop);
            buttonRefresh = new Button(hargTop);
            buttonRefresh.Image("buttonRefresh.png");
            buttonRefresh.Width(50);
            buttonRefresh.Height(50);
            buttonReturn = new Button(hargTop);
            buttonReturn.Width(20);
            buttonReturn.Height(20);
            padTwo=new Label(hargTop);
            // debugLabel and padTwo are used to centre the refresh button
            debugLabel.WidthPercent(40);
            debugLabel.TextColor(Component.COLOR_WHITE);
            debugLabel.Text("Debug");
            debugLabel.FontSize(8);
            padTwo.WidthPercent(40);
            webviewMainDisplay = new fachtnaWebViewer(vargParent);
            webviewMainDisplay.UsesLocation(false);
            webviewMainDisplay.ClearCaches();
            webviewMainDisplay.FollowLinks(true);
            webviewMainDisplay.HomeUrl(home_url);
            webviewMainDisplay.HeightPercent(100);
            webviewMainDisplay.WidthPercent(100);
            webviewMainDisplay.IgnoreSslErrors(true);
            webviewMainDisplay.WebViewString("empty");
            webviewMainDisplay.GoHome();

            messages = new Notifier(vargParent);
            messages.BackgroundColor(Component.COLOR_RED);
            messages.TextColor(Component.COLOR_WHITE);

            EventDispatcher.registerEventForDelegation( this, formName, "Click" );
            EventDispatcher.registerEventForDelegation( this, formName, "GotText" );
            EventDispatcher.registerEventForDelegation( this, formName, "fachtnaWebViewStringChange" );
        }

        public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params ) {
            dbg("Component: " + componentName + " Event: " + eventName);
            if( eventName.equals("Click") ) {
                if (component.equals(buttonRefresh)) {
                    RefreshButton_Click();
                    return true;
                }
                else if (component.equals(buttonReturn)) {
                    startNewForm("MainActivity", null);
                    return true;
                }
            }
            else if( eventName.equals("fachtnaWebViewStringChange") ) {
                if (component.equals(webviewMainDisplay)) {
                    webView_Choice(webviewMainDisplay.WebViewString());
                    return true;
                }
            }
            return false;
        }

        public void RefreshButton_Click() {
            webviewMainDisplay.GoHome();
        }

        public void webView_Choice (final String theString) {
            if (theString.equals("") || (theString.equals("null")) ) {
                messages.BackgroundColor(colour_bad);
                messages.ShowAlert("There was an error loading the file");
                return;
            }
            if (theString.equals("xam")) {  // "An exam"
                messages.BackgroundColor(colour_bad);
                messages.ShowAlert("Written exams are unavailable in advance");
                return;
            }
            if (theString.equals("dna")) {  // "Does not apply"
                messages.BackgroundColor(colour_bad);
                messages.ShowAlert("Does not apply");
                return;
            }
            messages.BackgroundColor(colour_good);
            messages.ShowAlert("Loading the file now");
            lastWebViewString=theString;
            // https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Stuff that updates the UI
                    debugLabel.Text( theString );
                    // https://stackoverflow.com/questions/23240469/open-online-pdf-file-through-android-intent
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(base_url+theString));
                    startActivity(browserIntent);
                }
            });
        }
        void dbg (String debugMsg) {
            System.err.print( "~~~> " + debugMsg + " <~~~\n");
        }
    }
