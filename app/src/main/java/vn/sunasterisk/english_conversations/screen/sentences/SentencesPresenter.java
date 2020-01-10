package vn.sunasterisk.english_conversations.screen.sentences;

import android.content.Context;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.data.model.Sentence;
import vn.sunasterisk.english_conversations.utils.AudioDownloader;

public class SentencesPresenter implements SentencesContract.Presenter, AudioDownloader.AudioDownloaderListener {

    private SentencesContract.View mView;
    private Conversation mConversation;
    private AudioDownloader mAudioDownloader;

    public SentencesPresenter(SentencesContract.View view, Conversation conversation) {
        mView = view;
        mConversation = conversation;
        mAudioDownloader = new AudioDownloader(this);
    }

    @Override
    public void getSentences() {
        List<Sentence> sentences = mConversation.getSentences();
        if (sentences.size() == 0) {
            Context context = (Context) mView;
            if (context == null) {
                return;
            }
            mView.onGetSentencesFailure(context.getString(R.string.message_sentences_empty));
        } else {
            mView.onGetSentencesSuccess(sentences);
        }
    }

    @Override
    public void downloadAudio() {
        mAudioDownloader.execute(mConversation);
    }

    @Override
    public void onDownloadAudioSuccess() {
        mView.onDownloadAudioSuccess();
    }

    @Override
    public void onDownloadAudioFailure(String message) {
        mView.onDownloadAudioFailure(message);
    }
}
