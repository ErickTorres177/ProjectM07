package cat.copernic.erick.projectm07;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainAplication extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aplication);


    }
}



/*
*
*
<?xml version="1.0" encoding="utf-8"?>



<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="cat.copernic.erick.projectm07.MainAplication">


    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:scrollbars="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


    <androidx.drawerlayout.widget.DrawerLayout
        android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    tools:openDrawer="start">

    <include
        layout="@layout/app_bar_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.google.android.material.navigation.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        app:headerLayout="@layout/nav_header_main"
        app:menu="@menu/activity_main_drawer" />


    <androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/tvMuestraUsuario"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainAplication">

    <TextView
        android:id="@+id/tvMuestraUser"
        android:layout_width="252dp"
        android:layout_height="71dp"
        android:layout_marginTop="96dp"
        android:text="@string/muestra_usuario"
        android:textSize="20sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.444"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btnCerrarSesion"
        android:layout_width="137dp"
        android:layout_height="40dp"
        android:background="@drawable/botones"
        android:onClick="cerrarSesion"
        android:text="@string/Cerrar_Sesion"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.941"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvMuestraUser"
        app:layout_constraintVertical_bias="0.873" />

        <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="404dp"
            android:layout_marginEnd="12dp"
            android:src="@drawable/ic_reset"
            android:tint="@android:color/white"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.987"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.377" />
    </androidx.constraintlayout.widget.ConstraintLayout>




</androidx.drawerlayout.widget.DrawerLayout>




</RelativeLayout>

*
*
* */