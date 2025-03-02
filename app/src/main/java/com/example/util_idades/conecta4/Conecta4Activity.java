package com.example.util_idades.conecta4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.util_idades.R;
import com.example.util_idades.SelectorTemas;

import java.util.Arrays;

public class Conecta4Activity extends AppCompatActivity implements View.OnClickListener {

    private boolean esTurnoJugador1 = true;
    private boolean seInicioJuego = false;

    private int[][] tableroTrasero = new int[6][7];
    private ImageView[][] tableroVisual = new ImageView[6][7];

    TextView txtTurno;
    GridLayout gridTablero;
    Button botonEmpezar, botonTerminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SelectorTemas.aplicarTema(this);
        setContentView(R.layout.activity_conecta4);
        getSupportActionBar().hide();

        txtTurno = findViewById(R.id.txtJuegoTurno);

        gridTablero = findViewById(R.id.gridJuegoTablero);

        botonEmpezar = findViewById(R.id.bJuegoEmpezar);
        botonEmpezar.setOnClickListener(this);

        botonTerminar = findViewById(R.id.bJuegoTerminar);
        botonTerminar.setOnClickListener(this);

        crearTablero();
    }

    private void crearTablero() {
        seInicioJuego = false;

        for (int[] fila : tableroTrasero) {
            Arrays.fill(fila, 0);
        }

        gridTablero.removeAllViews();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int fila = i;
                int columna = j;

                ImageView imagenTablero = new ImageView(this);
                imagenTablero.setImageDrawable(getResources().getDrawable(R.drawable.conecta4_cajon));

                GridLayout.LayoutParams parametros = new GridLayout.LayoutParams();
                float densidad = getResources().getDisplayMetrics().density;
                parametros.height = (int) (50 * densidad);
                parametros.width = (int) (50 * densidad);
                imagenTablero.setLayoutParams(parametros);

                imagenTablero.setOnClickListener(v -> hacerMovimiento(fila, columna));

                tableroVisual[fila][columna] = imagenTablero;
                gridTablero.addView(imagenTablero);
            }
        }
        revisarInicioDeJuego();
    }

    private void revisarInicioDeJuego() {
        if (seInicioJuego) {
            txtTurno.setVisibility(View.VISIBLE);
            txtTurno.setText("Turno del Jugador 1");
            botonEmpezar.setVisibility(View.GONE);
            botonTerminar.setVisibility(View.VISIBLE);

        } else {
            txtTurno.setVisibility(View.GONE);
            txtTurno.setText(null);
            botonEmpezar.setVisibility(View.VISIBLE);
            botonTerminar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bJuegoEmpezar:
                seInicioJuego = true;
                esTurnoJugador1 = true;
                revisarInicioDeJuego();
                break;
            case R.id.bJuegoTerminar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Reinicio");
                builder.setMessage("El juego se va a reiniciar");
                builder.setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss());
                builder.show();

                crearTablero();
                break;
        }
    }

    private void hacerMovimiento(int fila, int columna) {
        if (!seInicioJuego) {
            Toast.makeText(this, "Debes pulsar el botón de empezar la partida", Toast.LENGTH_SHORT).show();
            return;
        }

        int jugadorActual = esTurnoJugador1 ? 1 : 2;
        int colorFicha = esTurnoJugador1 ? R.drawable.conecta4_cajonrojo : R.drawable.conecta4_cajonamarillo;

        if (!colocarFicha(fila, columna, jugadorActual, colorFicha)) {
            return;
        }
        // Verifica si el jugador actual ha ganado
        if (revisarVictoria(fila, columna, jugadorActual)) {
            mostrarMensajeVictoria("Jugador " + jugadorActual);
            seInicioJuego = false; // Termina el juego
            return;
        }
        if (quedoEmpate()) {
            mostrarMensajeEmpate();
            seInicioJuego = false;
            return;
        }
            // Cambia el turno
            esTurnoJugador1 = !esTurnoJugador1;
            String textoTurnoJugador = "Turno del Jugador " + (esTurnoJugador1 ? "1" : "2");
            txtTurno.setText(textoTurnoJugador);
            /*else {
            Toast.makeText(this, "Movimiento no válido. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
        }*/
    }

    private boolean colocarFicha(int fila, int columna, int numeroJugador, int colorCajon) {
        if (tableroTrasero[0][columna] != 0) {
            Toast.makeText(this, "La columna ya esta llena, prueba en otro lado", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (int i = 5; i >= 0; i--) {
            if (tableroTrasero[i][columna] == 0) {
                tableroVisual[i][columna].setImageDrawable(getResources().getDrawable(colorCajon));
                tableroTrasero[i][columna] = numeroJugador;
                return true;
            }

        }
        return false;
    }

    private boolean quedoEmpate() {
        for (int fila = 0; fila < tableroTrasero.length; fila++) {
            for (int columna = 0; columna < tableroTrasero[fila].length ; columna++) {
                if (tableroTrasero[fila][columna] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean revisarVictoria(int fila, int columna, int numeroJugador) {
        if (revisarHorizontal(fila,columna,numeroJugador)) {
            return true;
        }
        if (revisarVertical(fila,columna,numeroJugador)) {
            return true;
        }
        if (revisarDiagonalAscendente(fila,columna,numeroJugador)) {
            return true;
        }
        if (revisarDiagonalDescendente(fila,columna,numeroJugador)) {
            return true;
        }

        return false;
    }

    private boolean revisarHorizontal(int fila, int columna, int numeroJugador) {
        int contadorFichas = 0;

        for (int i = 0; i < tableroTrasero[fila].length; i++) {
            if (tableroTrasero[fila][i] == numeroJugador) {
                contadorFichas++;
                if (contadorFichas == 4) {
                    return true;
                }
            } else {
                contadorFichas = 0;
            }
        }
        return false;
    }

    private boolean revisarVertical(int fila, int columna, int numeroJugador) {
        int contadorFichas = 0;

        for (int i = 0; i < tableroTrasero.length; i++) {
            if (tableroTrasero[i][columna] == numeroJugador) {
                contadorFichas++;
                if (contadorFichas == 4) {
                    return true;
                }
            } else {
                contadorFichas = 0;
            }
        }
        return false;
    }

    private boolean revisarDiagonalAscendente(int fila, int columna, int numeroJugador) {
        int contadorFichas = 0;

        // Revisar la diagonal ascendente desde la posición dada
        for (int i = fila, j = columna; i >= 0 && j < 7; i--, j++) {
            if (tableroTrasero[i][j] == numeroJugador) {
                contadorFichas++;
                if (contadorFichas == 4) {
                    return true;
                }
            } else {
                contadorFichas = 0;
            }
        }

        // Revisar la diagonal ascendente desde la posición dada hacia abajo-izquierda
        for (int i = fila + 1, j = columna - 1; i < 6 && j >= 0; i++, j--) {
            if (tableroTrasero[i][j] == numeroJugador) {
                contadorFichas++;
                if (contadorFichas == 4) {
                    return true;
                }
            } else {
                contadorFichas = 0;
            }
        }

        return false;
    }

    private boolean revisarDiagonalDescendente(int fila, int columna, int numeroJugador) {
        int contadorFichas = 0;

        // Revisar la diagonal descendente desde la posición dada
        for (int i = fila, j = columna; i < 6 && j < 7; i++, j++) {
            if (tableroTrasero[i][j] == numeroJugador) {
                contadorFichas++;
                if (contadorFichas == 4) {
                    return true;
                }
            } else {
                contadorFichas = 0;
            }
        }

        // Revisar la diagonal descendente desde la posición dada hacia arriba-izquierda
        for (int i = fila - 1, j = columna - 1; i >= 0 && j >= 0; i--, j--) {
            if (tableroTrasero[i][j] == numeroJugador) {
                contadorFichas++;
                if (contadorFichas == 4) {
                    return true;
                }
            } else {
                contadorFichas = 0;
            }
        }

        return false;
    }

    private void mostrarMensajeVictoria(String numeroJugador) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Felicidades " + numeroJugador + "!");
        builder.setMessage("¡Has ganado la partida!");
        builder.setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void mostrarMensajeEmpate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Empate!");
        builder.setMessage("¡La partida acabo en tablas!");
        builder.setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}