package com.example.kot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.kot.R.id.photo_gallery_recycler_view;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity";
    private RecyclerView photoRecyclerView;
    private List<Gallery_item> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoRecyclerView = findViewById(R.id.photo_gallery_recycler_view);
        photoRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        new FetchItemTask().execute();
        setupAdapter();
    }
    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;
        public PhotoHolder( View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(photo_gallery_recycler_view);
        }
    }
    private  class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<Gallery_item>mGalleryItems;
        public PhotoAdapter(List<Gallery_item> items) {
            mGalleryItems = items;
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater =LayoutInflater.from(MainActivity.this);
            View v = inflater.inflate(R.layout.gallery_item, parent , false);
            return new PhotoHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            Gallery_item galleryItem = mGalleryItems.get(position);
            Picasso.with(MainActivity.this).load(galleryItem.getUrl()).into(holder.itemImageView);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();

        }
    }
    private class FetchItemTask extends AsyncTask<Void, Void, List<Gallery_item>> {

        @Override
        protected List<Gallery_item> doInBackground(Void... voids) {
            return new Kat_class().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Gallery_item> items) {
            mItems = items;
            setupAdapter();
        }
    }

    public class FetchItemTaskImpl extends FetchItemTask { }


    private void setupAdapter() {
        photoRecyclerView.setAdapter(new PhotoAdapter(mItems));
    }

}