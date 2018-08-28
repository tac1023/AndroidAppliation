package edu.unh.cs.cs619.bulletzone.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
//import android.support.v4.content.ContextCompat;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;

import edu.unh.cs.cs619.bulletzone.R;

/**
 * Essentially takes the FieldEntities stored in Battlefield and
 * uses their resources/pictures to create a display in GridView
 * that is visible on the screen.
 *
 * @author ?, Tyler Currier
 * @version 2.0
 * @since 3/31/2018
 */
@EBean
public class GridAdapter extends BaseAdapter {

    //Class Variables:
    private final Object monitor = new Object();
    @SystemService
    protected LayoutInflater inflater;
    //private int[][] mEntities = new int[16][16];
    private Integer[] mThumbIds = new Integer[16*16];
    private Integer[] tankThumbIds = new Integer[16*16];
    private LayerDrawable[] merged = new LayerDrawable[16*16];
    private Battlefield battlefield;
    private Resources resources;
    private Activity activity;
    private GridAdapter _instance = this;
    private Handler handler;
    //private boolean firstTime = true; //for singleton initialization.
    //End Class Variables

    /**
     * Constructor.
     */
    public GridAdapter()
    {
        _instance = this;
        handler = new Handler();
    }

    //Public Methods:

    /*/**
     * Sets the activity for the class.
     * @param activity
     */
    /*public void setActivity(Activity activity)
    {
        this.activity = activity;
    }*/


    /**
     * Pass in a new Battlefield and use it to update what
     * is shown on the screen.
     *
     * @param bField The new Battlefield to display
     */
    public void updateList(Battlefield bField) {
        synchronized (monitor) {
            //this.mEntities = entities;
            battlefield = bField;
            setImages();
            /*try {
                //System.out.println("ln 78");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //System.out.println("ln 82");
                        _instance.notifyDataSetChanged();
                        //System.out.println("ln 84");
                    }
                });
                /*handler.postAtFrontOfQueue(new Runnable() {
                    @Override
                    public void run() {
                        _instance.notifyDataSetChanged();
                    }
                });*/
            /*}
            catch(NullPointerException e)
            {
                if(_instance == null)
                    Log.d("GridAdapter", "Update failed due to Activity restart");
                else if(activity == null) {
                    //Log.d("GridAdapter", "Reference to Activity is null");
                    handler.postAtFrontOfQueue(new Runnable() {
                    @Override
                    public void run() {
                        _instance.notifyDataSetChanged();
                    }
                });
                }
            }*/
        }
    }

    /**
     * Sets the resources passes by an activity.
     * @param resources
     */
    public void setResources(Resources resources)
    {
        this.resources = resources;
    }

    /**
     * Gets the number of items in the grid
     *
     * @return Number of cells in the grid
     */
    @Override
    public int getCount() {
        return (16 * 16);
    }

    /**
     * Return the item at the indicated position.
     *
     * @param position position at which to get item
     * @return The item to return
     */
    @Override
    public Object getItem(int position)
    {
        //return mEntities[(int) position / 16][position % 16];
        return mThumbIds[position];
    }

    /**
     * Return the ID of the item at the give position
     *
     * @param position Position of item to access
     * @return ID of item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get what the cell is supposed to look like at the given position
     *
     * @param position The position of the cell whose view to get
     * @param convertView Previous view if applicable?
     * @param parent The ViewGroup that this view is a part of
     * @return The view of the cell
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.field_item, null);
        }


        if (convertView instanceof ImageView) {
            synchronized (monitor) {

                ((ImageView)convertView).setLayoutParams(new GridView.LayoutParams(50, 50));
                ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                convertView.setPadding(0,0,0,0);

                if(mThumbIds[position] != null)
                    ((ImageView) convertView).setImageDrawable(merged[position]);
                else
                    ((ImageView) convertView).setImageResource(R.drawable.blank);
            }
        }

        return convertView;
    }
    //End Public Methods

    //Private Methods:
    /**
     * Fill the mThumbIds and tankThumbIds arrays with the pictures/resources associated
     * with the FieldEntity from Battlefield at the given index. Then combine these
     * images into a single layered drawable to be displayed on the screen.
     */
    private void setImages()
    {
        mThumbIds = new Integer[battlefield.getSize()];
        tankThumbIds = new Integer[battlefield.getSize()];
        merged = new LayerDrawable[battlefield.getSize()];
        for(int i = 0; i < battlefield.getSize(); i++)
        {
            mThumbIds[i] = battlefield.getFieldEntity(i).getResourceID();
            tankThumbIds[i] = battlefield.getTankEntity(i).getResourceID();

            Drawable[] layers = new Drawable[2];
            layers[0] = resources.getDrawable(mThumbIds[i]);
            layers[1] = resources.getDrawable(tankThumbIds[i]);

            merged[i] = new LayerDrawable(layers);
        }
    }
    //End Private Methods
}


