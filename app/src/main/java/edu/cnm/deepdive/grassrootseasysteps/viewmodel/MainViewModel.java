package edu.cnm.deepdive.grassrootseasysteps.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.HashSet;
import java.util.Set;

public class MainViewModel extends AndroidViewModel {

  private final MutableLiveData<Set<String>> permissions;

  public MainViewModel(@NonNull Application application) {
    super(application);
    permissions = new MutableLiveData<>(new HashSet<>());
  }

  public LiveData<Set<String>> getPermissions() {
    return permissions;
  }

  public void grantPermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    if (permissions.add(permission)) {
      this.permissions.setValue(permissions);
    }
  }

  public void revokePermission(String permission) {
    Set<String> permissions = this.permissions.getValue();
    if(permissions.remove(permission)) {
      this.permissions.setValue(permissions);
    }

  }
}
