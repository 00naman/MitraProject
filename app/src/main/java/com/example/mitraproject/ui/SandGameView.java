package com.example.mitraproject.ui;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

import java.util.*;

public class SandGameView extends View {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final List<SandParticle> particles = new ArrayList<>();
    private final Random random = new Random();
    private final int maxParticles = 1000; // limit particles

    public SandGameView(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
        post(updateRunnable);
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

            for (int i = 0; i < 6; i++) { // reduce creation rate
                if (particles.size() >= maxParticles) {
                    particles.remove(0); // recycle old particles
                }
                particles.add(new SandParticle(baseX + random.nextInt(16) - 8, baseY));
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
            postDelayed(this, 16); // ~60 FPS
        }
    };

    private void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            SandParticle p = particles.get(i);
            if (!p.settled) {
                p.vy += 0.5f;
                p.y += p.vy;
                p.x += p.vx;

                if (p.y + p.size >= getHeight()) {
                    p.y = getHeight() - p.size;
                    p.settled = true;
                    continue;
                }

                for (int j = 0; j < particles.size(); j++) {
                    if (i == j) continue;
                    SandParticle other = particles.get(j);
                    if (!other.settled) continue;

                    float dx = p.x - other.x;
                    float dy = (p.y + p.size) - other.y;

                    if (Math.abs(dx) < p.size && dy >= 0 && dy < p.size * 1.2f) {
                        p.settled = true;
                        break;
                    }
                }
            }
        }
    }

    private static class SandParticle {
        float x, y;
        float vx, vy;
        float size;
        int color;
        boolean settled;

        static final int[] COLORS = {
                Color.parseColor("#FFC1CC"),
                Color.parseColor("#ADD8E6"),
                Color.parseColor("#FFFACD"),
                Color.parseColor("#C1FFC1"),
                Color.parseColor("#E6E6FA")
        };

        public SandParticle(float x, float y) {
            this.x = x;
            this.y = y;
            this.vx = (new Random().nextFloat() - 0.5f) * 1.2f;
            this.vy = 0;
            this.size = 9f;
            this.color = COLORS[new Random().nextInt(COLORS.length)];
            this.settled = false;
        }
    }
}
