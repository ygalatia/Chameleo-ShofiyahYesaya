package com.example.ye.chameleo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ye.chameleo.EditPicture;
import com.example.ye.chameleo.Model.Filters;
import com.example.ye.chameleo.R;

import java.util.List;

/**
 * Created by YE on 16/01/2018.
 */

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.FilterViewHolder> {

    private Context context;
    private List<Filters> listFilter;
    EditPicture editing;

    public FiltersAdapter(Context context, List<Filters> listFilter) {
        this.context = context;
        this.listFilter = listFilter;
    }

    @Override
    public FiltersAdapter.FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filters, parent, false);
        return new FiltersAdapter.FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FiltersAdapter.FilterViewHolder holder, int position) {
        final Filters filters = listFilter.get(position);
        String uri = "drawable/" + filters.getFilterPhoto();
        int icon = context.getResources().getIdentifier(uri, "drawable", context.getPackageName());
        holder.imgFilter.setImageResource(icon);
        holder.txtFilter.setText(filters.getFiltername());
        holder.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditPicture)context).editpic(filters.getFilterID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFilter.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFilter;
        TextView txtFilter;
        public FilterViewHolder(View itemView) {
            super(itemView);
            imgFilter = itemView.findViewById(R.id.img_filters);
            txtFilter = itemView.findViewById(R.id.txt_filtername);
        }
    }
}
