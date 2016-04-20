package aashrai.android.gettowork.adapter;

import aashrai.android.gettowork.R;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import java.util.List;
import java.util.Set;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

  private final List<ApplicationInfo> packageNames;
  private final Set<String> activatedPackages;
  private final PackageManager packageManager;
  private static final String TAG = "PackageListAdapter";

  public PackageListAdapter(List<ApplicationInfo> packageNames, Set<String> activatedPackages,
      PackageManager packageManager) {
    this.packageNames = packageNames;
    this.activatedPackages = activatedPackages;
    this.packageManager = packageManager;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.packageName.setText(packageNames.get(position).loadLabel(packageManager));
    holder.packageName.setChecked(activatedPackages.contains(packageNames.get(position).name));
    holder.thumbnail.setImageDrawable(packageNames.get(position).loadIcon(packageManager));
  }

  @Override public int getItemCount() {
    return packageNames.size();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_package, parent, false));
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.sw_activate) Switch packageName;
    @Bind(R.id.iv_thumbnail) ImageView thumbnail;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnCheckedChanged(R.id.sw_activate) public void activateForPackage(boolean flag) {
      Log.d(TAG, "activateForPackage() called with: " + "flag = [" + flag + "]");
      if (flag) {
        activatedPackages.add(packageName.getText().toString());
      } else {
        activatedPackages.remove(packageName.getText().toString());
      }
    }
  }
}
