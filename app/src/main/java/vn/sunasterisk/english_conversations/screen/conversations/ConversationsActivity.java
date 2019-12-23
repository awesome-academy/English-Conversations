package vn.sunasterisk.english_conversations.screen.conversations;

import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.sunasterisk.english_conversations.R;
import vn.sunasterisk.english_conversations.data.model.Category;
import vn.sunasterisk.english_conversations.data.model.Conversation;
import vn.sunasterisk.english_conversations.screen.base.BaseActivity;
import vn.sunasterisk.english_conversations.screen.category.CategoryActivity;
import vn.sunasterisk.english_conversations.utils.SpacesItemDecoration;

public class ConversationsActivity extends BaseActivity implements ConversationsContract.View {

    private RecyclerView mRecyclerView;
    private ConversationsAdapter mConversationsAdapter;
    private ConversationsPresenter mConversationsPresenter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_conversations;
    }

    @Override
    protected void initComponents() {
        mRecyclerView = findViewById(R.id.recycler_conversations);

        Intent intent = getIntent();
        Category category = (Category) intent.getSerializableExtra(CategoryActivity.CATEGORY_NAME);
        if (category == null) {
            return;
        }

        mConversationsPresenter = new ConversationsPresenter(this, category);
        mConversationsAdapter = new ConversationsAdapter();
        mRecyclerView.setAdapter(mConversationsAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp_4);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        setTitle(category.getTitle());

        mConversationsPresenter.getConversations();
    }

    @Override
    protected void registerListeners() {
    }

    @Override
    public void onGetConversationSuccess(List<Conversation> conversations) {
        mConversationsAdapter.setConversations(conversations);
        mConversationsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetConversationFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
