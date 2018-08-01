package com.eno.attendence.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.eno.attendence.R;
import com.eno.attendence.listener.RecyclerItemViewClickListener;
import com.eno.attendence.modules.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daya on 8/10/17.
 */

public class EmployeeAdapter extends RecyclerView.Adapter implements Filterable {

    private List<Employee> originalFeedListData;
    private List<Employee> filterFeedListData;
    private ItemFilter mFilter = new ItemFilter();
    Activity mContext;
    static RecyclerItemViewClickListener listListener;


    public EmployeeAdapter(final Activity context, List feedData) {
        mContext = context;
        this.originalFeedListData = feedData;
        this.filterFeedListData = feedData;

    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtEmpName, txtEmpId;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listListener != null) {
                        listListener.onClickListener(getAdapterPosition());
                    }
                }
            });
            this.txtEmpName = itemView.findViewById(R.id.txtEmpName);
            this.txtEmpId = itemView.findViewById(R.id.txtEmpId);


        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_employee, parent, false);
        return new TextTypeViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return -1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final Employee object = filterFeedListData.get(listPosition);
        TextTypeViewHolder txtHolder = (TextTypeViewHolder) holder;

        txtHolder.txtEmpId.setText(object.getEmpId());
        txtHolder.txtEmpName.setText(object.getName());


    }

    public void addItemsAndNotify(List<Employee> list) {
        if (list != null) {
            originalFeedListData.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return filterFeedListData.size();
    }

    public Employee getItem(int pos) {
        return filterFeedListData.get(pos);
    }

    public void setOnListItemClickListener(RecyclerItemViewClickListener clickListener) {
        listListener = clickListener;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Employee> mFilteredList = new ArrayList<>();
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                mFilteredList = originalFeedListData;
            } else {
                for (Employee c : originalFeedListData) {
                    if (c.getName().toLowerCase().contains(charString)) {
                        mFilteredList.add(c);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = mFilteredList;
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            filterFeedListData = (List<Employee>) results.values;
            notifyDataSetChanged();
        }
    }
}
