package com.example.mitraproject.ui;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

import java.util.*;

public class SandGameView extends View {

    private final Paint paint = new Paint();
    private final List<SandParticle> particles = new ArrayList<>();
    private final Random random = new Random();

    public SandGameView(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
        post(updateRunnable); // Start animation
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (SandParticle p : particles) {
            paint.setColor(p.color);
            canvas.drawCircle(p.x, p.y, p.size, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float baseX = event.getX();
            float baseY = event.getY();

            for (int i = 0; i < 10; i++) {
                float x = baseX + random.nextInt(20) - 10;
                float y = baseY + random.nextInt(10) - 5;
                particles.add(new SandParticle(x, y));
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateParticles();
            invalidate();
            postDelayed(this, 16);
        }
    };

    private void updateParticles() {
        for (SandParticle p : particles) {
            // Only move if not yet "settled"
            if (!p.settled) {
                p.vy += 0.2f; // gravity
                p.y += p.vy;
                p.x += p.vx;

                // Check if it's at or below the floor
                if (p.y + p.size >= getHeight()) {
                    p.y = getHeight() - p.size;
                    p.vx = 0;
                    p.vy = 0;
                    p.settled = true;
                } else {
                    // Check collision with other particles
                    for (SandParticle other : particles) {
                        if (other == p || !other.settled) continue;
                        float dx = p.x - other.x;
                        float dy = (p.y + p.size) - other.y;
                        if (Math.abs(dx) < p.size && dy >= 0 && dy < p.size * 1.5) {
                            p.vy = 0;
                            p.vx = 0;
                            p.settled = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    // Particle class
    private static class SandParticle {
        float x, y;
        float vx, vy;
        float size;
        int color;
        boolean settled;

        public SandParticle(float x, float y) {
            this.x = x;
            this.y = y;
            this.vx = new Random().nextFloat() * 1.5f - 0.75f; // Horizontal drift
            this.vy = 0;
            this.size = 11f;
            this.settled = false;

            int[] colors = {
                    Color.parseColor("#FFC1CC"), // Pastel pink
                    Color.parseColor("#ADD8E6"), // Light blue
                    Color.parseColor("#FFFACD"), // Lemon
                    Color.parseColor("#C1FFC1"), // Light green
                    Color.parseColor("#E6E6FA")  // Lavender
            };
            this.color = colors[new Random().nextInt(colors.length)];
        }
    }
}
