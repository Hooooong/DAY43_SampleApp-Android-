package com.hooooong.socar.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hooooong.socar.R;
import com.hooooong.socar.domain.model.CarData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Hong on 2017-11-22.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private List<CarData> carList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CarData car = carList.get(position);
        holder.setTextCarName(car.getCar_name());
        holder.setTextZoneName(car.getZone_name());
        holder.setTextDiscount(car.getMember_discount());
        holder.setTextOptions(car.getCar_option());
        holder.setTextFee(car.getDriving_fee());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void setCarListAndRefresh(List<CarData> data){
        this.carList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textCarName;
        TextView textZoneName;
        TextView textOptions;
        TextView textDiscount;
        TextView textFee;

        public ViewHolder(View itemView) {
            super(itemView);

            textCarName = itemView.findViewById(R.id.textCarName);
            textZoneName = itemView.findViewById(R.id.textZoneName);
            textOptions = itemView.findViewById(R.id.textOptions);
            textDiscount = itemView.findViewById(R.id.textDiscount);
            textFee = itemView.findViewById(R.id.textFee);
        }

        public void setTextCarName(String carName) {
            textCarName.setText(carName);
        }

        public void setTextZoneName(String zoneName) {
            textZoneName.setText(zoneName);
        }

        public void setTextOptions(String options) {
            textOptions.setText(options);
        }

        public void setTextDiscount(String discount) {
           textDiscount.setText(discount);
        }

        public void setTextFee(String fee) {
            textFee.setText(fee);
        }
    }
}
