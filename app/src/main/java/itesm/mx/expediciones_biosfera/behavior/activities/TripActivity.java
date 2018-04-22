package itesm.mx.expediciones_biosfera.behavior.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.Map;

import itesm.mx.expediciones_biosfera.R;
import itesm.mx.expediciones_biosfera.database.operations.FirestoreDestinationHelper;
import itesm.mx.expediciones_biosfera.database.operations.FirestoreTripHelper;
import itesm.mx.expediciones_biosfera.entities.models.Destination;
import itesm.mx.expediciones_biosfera.entities.models.Trip;

public class TripActivity extends AppCompatActivity {
    FirebaseFirestore db;
    TextView tvLocation;
    TextView tvDate;
    TextView tvDescription;
    TextView tvPrice;
    TextView tvDuration;
    TextView tvCapacity;
    Map<String, Object> mapTrip, mapDestination;
    Destination destination;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        tvLocation = findViewById(R.id.tv_location);
        tvDate = findViewById(R.id.tv_date);
        tvDescription = findViewById(R.id.tv_description);
        tvPrice = findViewById(R.id.tv_price);
        tvDuration = findViewById(R.id.tv_duration);
        tvCapacity = findViewById(R.id.tv_capacity);

       // Destination destination = new Destination("Parras", "Coahuila", "Parras", 25.442905, -102.176722, "Pueblo magico", null);
//        Trip trip = new Trip("Titulo", Date.valueOf("2015-15-01"), 20, 500.13, 3, destination, null);
        db = FirebaseFirestore.getInstance();

        DocumentReference reference = db.collection("trips").document("53BlXS9FMqdp7c2QHxNn");
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    System.out.println("PPPPPPPP" + doc.getData());
                    mapTrip = doc.getData();
                    trip = FirestoreTripHelper.builTrip(mapTrip);

                    DocumentReference reference = (DocumentReference) mapTrip.get("destination");
                    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot snapshot = task.getResult();
                            System.out.println("SNAP" + snapshot.getData());
                            destination = FirestoreDestinationHelper.buildDestination(snapshot.getData());
                            System.out.println(destination);
                            trip.setDestination(destination);
                            System.out.println(trip);
                            tvLocation.setText(trip.getDestination().getState() + ", " + trip.getDestination().getCity());
                            System.out.println(trip.getTitle());
                            System.out.println(trip.getDestination().getCity());
                            getSupportActionBar().setTitle(trip.getTitle());
                            tvDescription.setText(trip.getDestination().getDescription());
                            tvCapacity.setText(String.valueOf(trip.getCapacity()));
                            tvPrice.setText(String.valueOf(trip.getPrice()));
                            tvDate.setText(trip.getDate().toString());
                            tvDuration.setText(String.valueOf(trip.getDuration()));


                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("FALIURE");
            }
        });



/*
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    Trip t = doc.toObject(Trip.class);
                    System.out.println("DEst" + t.getDestination().getDescription());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("FALIURE");
            }
        }); */


    }

    public void read(){

    }
}