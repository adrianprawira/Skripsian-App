package com.upnvj.skripsian;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.upnvj.skripsian.databinding.ActivityMainBinding;
import com.upnvj.skripsian.util.ServiceLocator;
import com.upnvj.skripsian.vm.MainViewModel;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Skripsian);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.lecturerFragment, R.id.thesisFragment).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, configuration);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
        navController.addOnDestinationChangedListener(this);

        final MainViewModel viewModel = new ViewModelProvider(this, ServiceLocator.mainFactory()).get(MainViewModel.class);
        binding.fabAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDestination currentDestination = navController.getCurrentDestination();
                if (currentDestination != null) {
                    int id = currentDestination.getId();
                    if (id == R.id.lecturerFragment) viewModel.triggerAddLecturerEvent();
                    else if (id == R.id.thesisFragment) viewModel.triggerAddThesisScheduleEvent();
                }
            }
        });
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        int id = destination.getId();
        if (id == R.id.homeFragment) {
            binding.appBar.setVisibility(View.GONE);
            binding.bottomNav.setVisibility(View.VISIBLE);
            binding.fabAction.hide();
        } else if (id == R.id.lecturerFragment || id == R.id.thesisFragment) {
            binding.appBar.setVisibility(View.VISIBLE);
            binding.bottomNav.setVisibility(View.VISIBLE);
            binding.fabAction.show();
        } else {
            binding.appBar.setVisibility(View.VISIBLE);
            binding.bottomNav.setVisibility(View.GONE);
            binding.fabAction.hide();
        }
    }

    public void setToolbarTitle(String title) {
        binding.toolbar.setTitle(title);
    }

    public void setToolbarTitle(int titleRes) {
        binding.toolbar.setTitle(titleRes);
    }

}