package org.petabytes.awesomeblogs.feeds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.petabytes.api.source.local.Entry;
import org.petabytes.awesomeblogs.AwesomeBlogsApp;
import org.petabytes.awesomeblogs.R;
import org.petabytes.awesomeblogs.summary.SummaryActivity;
import org.petabytes.coordinator.Coordinator;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.schedulers.Schedulers;

class EntryGradientCoordinator extends Coordinator {

    @BindView(R.id.title) TextView titleView;
    @BindView(R.id.author) TextView authorView;
    @BindView(R.id.summary) TextView summaryView;

    private final Context context;
    private final Entry entry;

    EntryGradientCoordinator(@NonNull Context context, @NonNull Entry entry) {
        this.context = context;
        this.entry = entry;
    }

    @Override
    public void attach(@NonNull View view) {
        super.attach(view);
        bind(AwesomeBlogsApp.get().api()
            .isRead(entry.getLink()), isRead -> {
                titleView.setText(entry.getTitle());
                titleView.setAlpha(isRead ? 0.65f : 1f);
                authorView.setText(Entry.getFormattedAuthorUpdatedAt(entry));
            });
        bind(Observable.just(entry.getSummary().trim())
            .map(summary -> Jsoup.parse(summary).text())
            .subscribeOn(Schedulers.io()), summary -> summaryView.setText(summary));
        setBackground(view);
    }

    @OnClick(R.id.container)
    void onContainerClick() {
        context.startActivity(SummaryActivity.intent(context,
            entry.getTitle(), entry.getAuthor(), entry.getUpdatedAt(), entry.getSummary(), entry.getLink()));
    }

    private void setBackground(@NonNull View view) {
        switch (new Random().nextInt(8)) {
            case 0: view.setBackgroundResource(R.drawable.background_gradient_0); break;
            case 1: view.setBackgroundResource(R.drawable.background_gradient_1); break;
            case 2: view.setBackgroundResource(R.drawable.background_gradient_2); break;
            case 3: view.setBackgroundResource(R.drawable.background_gradient_3); break;
            case 4: view.setBackgroundResource(R.drawable.background_gradient_4); break;
            case 5: view.setBackgroundResource(R.drawable.background_gradient_5); break;
            case 6: view.setBackgroundResource(R.drawable.background_gradient_6); break;
            case 7: view.setBackgroundResource(R.drawable.background_gradient_7); break;
        }
    }
}
