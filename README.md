# Dynamic Android views for various screen sizes

#### By: Ryan Jones
#### Date: 06/25/2017

### Google Doc: [Link to Document](https://docs.google.com/document/d/156tvgPxx0o7WsyVf394hrt6NM92XxSI6w8Nr2IJHSJU/edit?usp=sharing)

## Description
This is a tutorial for creating dynamic views in Android that will adjust based off the screen size the user is loading your app on.

I’ve been struggling to figure out how to dynamically change my app so that it will scale properly for different screen sizes. After, a lot of google searches and stack overflow reading I started to look at Android design in a completely different way.
 
Creating layouts is very easy using Android Studio and all though there is a robust amount of different views or layouts a problem still arises. How do I make this app work for everyone rather than a specific phone screen? When using Android Studio you can configure multiple emulators for various Android devices and each one has slightly different dimensions. 
 
For my specific project, I needed to create a 2x3 (2 columns x 3 rows) and so I tried various layout types to achieve this. My thought process, since there are different types of layouts like grid layout, Linear Layout, Relative Layout, etc.. One of the layouts would probably help me achieve this task. What I didn’t realize is that no matter what layout I choose, there is still going to be code that has to work out the size of the screen and then set each cell dynamically. In my case, the 2x3 grid was simply six buttons.
 
What I found, is that the layout really doesn’t matter in regard to arranging views to be dynamic based on varying screen sizes. For other scenarios, let’s say you were making an API call to Flickr for photos and then wanted those to be positioned in a 2x3 grid which the user could scroll through you would use a Grid View or a Recycler View with a grid layout manager, but there would still be some math involved in working out how much space (height/width) each cell would be.
 
## How to get the total height of your screen
```
RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
DisplayMetrics displaymetrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
int rlWidth = displaymetrics.widthPixels;
int rlHeight = displaymetrics.heightPixels;
System.out.println("Screen Width: " + rlWidth);
System.out.println("Screen Height: " + rlHeight);
```
 
This is great for getting the ‘total’ height of your screen, but this doesn’t account for the status bar height and the action bar height.

## How to get the height of your Action Bar
``` System.out.println("Action Bar Height: " + getActionBarHeight()); // in the onCreate method ```

```
private int getActionBarHeight() {
   int actionBarHeight = getSupportActionBar().getHeight();
   if (actionBarHeight != 0)
       return actionBarHeight;
   final TypedValue tv = new TypedValue();
   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
       if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
           actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
   } else if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
       actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
   return actionBarHeight;
}
```

I’ve confirmed this method works. I came across ‘getActionBarHeight()’ reading stack overflow, here is a link to the question/answer.. https://stackoverflow.com/a/27970879
 
## How to get the height of your Status Bar
``` System.out.println("Status Bar Height: " + getStatusBarHeight()); // in the onCreate method ```

```
public int getStatusBarHeight() {
   int result = 0;
   int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
   if (resourceId > 0) {
       result = getResources().getDimensionPixelSize(resourceId);
   }
   return result;
}
```
 
I’ve confirmed this method works. I came across ‘getStatusBarHeight()’ reading stack overflow, here is a link to the question/answer.. https://stackoverflow.com/a/3356263
 
## How to do the math for a 2x3 layout
In my case, I’m using six buttons (two buttons x three buttons). This code is inside my onCreate() method.

```
DisplayMetrics displaymetrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
int rlWidth = displaymetrics.widthPixels;
int rlHeight = displaymetrics.heightPixels;
 
// the math to account for the action bar for dividing height equally by 3
int btnWidth = rlWidth / 2;
int btnHeight = (rlHeight - getActionBarHeight() - getStatusBarHeight()) / 3;
```

This code allows me to get the height and width needed for each button in my 2x3 layout. Now I need to attach the height/width to each button.
 
## Attaching the Height/Width to the buttons
I’m using a hybrid approach to the UI, where I declare my buttons in the XML, give each button an id, then connect them to my Java code. The code below is in the onCreate method.

```
// connection buttons
Button btn1 = (Button) findViewById(R.id.btn1);
Button btn2 = (Button) findViewById(R.id.btn2);
Button btn3 = (Button) findViewById(R.id.btn3);
Button btn4 = (Button) findViewById(R.id.btn4);
Button btn5 = (Button) findViewById(R.id.btn5);
Button btn6 = (Button) findViewById(R.id.btn6);
 
// getting the total screen size, height/width
DisplayMetrics displaymetrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
int rlWidth = displaymetrics.widthPixels;
int rlHeight = displaymetrics.heightPixels;
 
// calculating height/width for each button
int btnWidth = rlWidth / 2;
int btnHeight = (rlHeight - getActionBarHeight() - getStatusBarHeight()) / 3;
 
// setting each button's height/width
btn1.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
btn2.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
btn3.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
btn4.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
btn5.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
btn6.setLayoutParams(new RelativeLayout.LayoutParams(btnWidth, btnHeight));
```

This code combines everything so far, except the methods ‘getActionBarHeight()’ and ‘getStatusBarHeight()’ which you can find above.

## Positioning the buttons and updating the UI
This is not the cleanest code, I’m sure. However, this does work and writing it out the long form way was easier for me to wrap my head around. What we are doing is telling each button how to position itself on the screen. Button 1 (btn1), does not need this due to it being preset at the 0,0 naturally.

```
/** Button 2 */
RelativeLayout.LayoutParams btn2Params = (RelativeLayout.LayoutParams)btn2.getLayoutParams();
btn2Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
btn2Params.addRule(RelativeLayout.LEFT_OF, R.id.btn1);
btn2.setLayoutParams(btn2Params); //causes layout update
 
/** Button 3 */
RelativeLayout.LayoutParams btn3Params = (RelativeLayout.LayoutParams)btn3.getLayoutParams();
btn3Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
btn3Params.addRule(RelativeLayout.BELOW, R.id.btn1);
btn3.setLayoutParams(btn3Params);
 
/** Button 4 */
RelativeLayout.LayoutParams btn4Params = (RelativeLayout.LayoutParams)btn4.getLayoutParams();
btn4Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
btn4Params.addRule(RelativeLayout.BELOW, R.id.btn2);
btn4Params.addRule(RelativeLayout.LEFT_OF, R.id.btn3);
btn4.setLayoutParams(btn4Params);
 
/** Button 5 */
RelativeLayout.LayoutParams btn5Params = (RelativeLayout.LayoutParams)btn5.getLayoutParams();
btn5Params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
btn5Params.addRule(RelativeLayout.BELOW, R.id.btn3);
btn5.setLayoutParams(btn5Params);
 
/** Button 6 */
RelativeLayout.LayoutParams btn6Params = (RelativeLayout.LayoutParams)btn6.getLayoutParams();
btn6Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
btn6Params.addRule(RelativeLayout.BELOW, R.id.btn4);
btn6Params.addRule(RelativeLayout.LEFT_OF, R.id.btn5);
btn6.setLayoutParams(btn6Params);
```

Final Result..

![Final Result](https://github.com/Znergy/dynamic-android-for-various-screen-sizes/blob/master/app/src/main/res/drawable/finalresult.png)

