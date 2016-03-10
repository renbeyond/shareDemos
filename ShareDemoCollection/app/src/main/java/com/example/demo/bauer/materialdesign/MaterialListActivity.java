package com.example.demo.bauer.materialdesign;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.CardProvider;
import com.dexafree.materialList.card.OnActionClickListener;
import com.dexafree.materialList.card.action.TextViewAction;
import com.dexafree.materialList.card.action.WelcomeButtonAction;
import com.dexafree.materialList.card.provider.ListCardProvider;
import com.dexafree.materialList.listeners.OnDismissCallback;
import com.dexafree.materialList.listeners.RecyclerItemClickListener;
import com.dexafree.materialList.view.MaterialListView;
import com.example.demo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bauer_bao on 16/3/9.
 */
public class MaterialListActivity extends AppCompatActivity {
    private Context mContext;
    private MaterialListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bauer_activity_materiallist);

        // Save a reference to the context
        mContext = this;

        // Bind the MaterialListView to a variable
        mListView = (MaterialListView) findViewById(R.id.material_listview);

        final ImageView emptyView = (ImageView) findViewById(R.id.imageView);
        emptyView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mListView.setEmptyView(emptyView);
        Picasso.with(this)
                .load("https://www.skyverge.com/wp-content/uploads/2012/05/github-logo.png")
                .resize(100, 100)
                .centerInside()
                .into(emptyView);

        // Fill the array withProvider mock content
        fillArray();

        // Set the dismiss listener
        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(@NonNull Card card, int position) {
                // Show a toast
                Toast.makeText(mContext, "You have dismissed a " + card.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the ItemTouchListener
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Card card, int position) {
                Log.d("CARD_TYPE", "" + card.getTag());
            }

            @Override
            public void onItemLongClick(@NonNull Card card, int position) {
                Log.d("LONG_CLICK", "" + card.getTag());
            }
        });

    }

    private void fillArray() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            cards.add(getRandomCard(i));
        }
        mListView.getAdapter().addAll(cards);
    }

    @SuppressLint("ResourceAsColor")
    private Card getRandomCard(final int position) {
        String title = "Card number " + (position + 1);
        String description = "Lorem ipsum dolor sit amet";

        switch (position % 7) {
            case 0: {
                return new Card.Builder(this)
                        .setTag("SMALL_IMAGE_CARD")
                        .setDismissible()
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_small_image_card)
                        .setTitle(title)
                        .setDescription(description)
                        .setDrawable(R.drawable.sample_android)
                        .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                            @Override
                            public void onImageConfigure(@NonNull final RequestCreator requestCreator) {
                                requestCreator.rotate(position * 90.0f)
                                        .resize(150, 150)
                                        .centerCrop();
                            }
                        })
                        .endConfig()
                        .build();
            }
            case 1: {
                return new Card.Builder(this)
                        .setTag("BIG_IMAGE_CARD")
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_big_image_card_layout)
                        .setTitle(title)
                        .setSubtitle(description)
                        .setSubtitleGravity(Gravity.END)
                        .setDrawable("https://assets-cdn.github.com/images/modules/logos_page/GitHub-Mark.png")
                        .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                            @Override
                            public void onImageConfigure(@NonNull final RequestCreator requestCreator) {
                                requestCreator.rotate(position * 45.0f)
                                        .resize(200, 200)
                                        .centerCrop();
                            }
                        })
                        .endConfig()
                        .build();
            }
            case 2: {
                final CardProvider provider = new Card.Builder(this)
                        .setTag("BASIC_IMAGE_BUTTON_CARD")
                        .setDismissible()
                        .withProvider(new CardProvider<>())
                        .setLayout(R.layout.material_basic_image_buttons_card_layout)
                        .setTitle(title)
                        .setTitleGravity(Gravity.END)
                        .setDescription(description)
                        .setDescriptionGravity(Gravity.END)
                        .setDrawable(R.drawable.bauer_image1)
                        .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                            @Override
                            public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                                requestCreator.fit();
                            }
                        })
                        .addAction(R.id.left_text_button, new TextViewAction(this)
                                .setText("left")
                                .setTextResourceColor(R.color.color_5)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                                        card.getProvider().setTitle("CHANGED ON RUNTIME");
                                    }
                                }))
                        .addAction(R.id.right_text_button, new TextViewAction(this)
                                .setText("right")
                                .setTextResourceColor(R.color.color_5)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        Toast.makeText(mContext, "You have pressed the right button on card " + card.getProvider().getTitle(), Toast.LENGTH_SHORT).show();
                                        card.dismiss();
                                    }
                                }));

                if (position % 2 == 0) {
                    provider.setDividerVisible(true);
                }

                return provider.endConfig().build();
            }
            case 3: {
                final CardProvider provider = new Card.Builder(this)
                        .setTag("BASIC_BUTTONS_CARD")
                        .setDismissible()
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_basic_buttons_card)
                        .setTitle(title)
                        .setDescription(description)
                        .addAction(R.id.left_text_button, new TextViewAction(this)
                                .setText("left")
                                .setTextResourceColor(R.color.color_5)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                                    }
                                }))
                        .addAction(R.id.right_text_button, new TextViewAction(this)
                                .setText("right")
                                .setTextResourceColor(R.color.color_5)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                                    }
                                }));

                if (position % 2 == 0) {
                    provider.setDividerVisible(true);
                }

                return provider.endConfig().build();
            }
            case 4: {
                final CardProvider provider = new Card.Builder(this)
                        .setTag("WELCOME_CARD")
                        .setDismissible()
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_welcome_card_layout)
                        .setTitle("Welcome Card")
                        .setTitleColor(Color.WHITE)
                        .setDescription("I am the description")
                        .setDescriptionColor(Color.WHITE)
                        .setSubtitle("My subtitle!")
                        .setSubtitleColor(Color.WHITE)
                        .setBackgroundColor(Color.BLUE)
                        .addAction(R.id.ok_button, new WelcomeButtonAction(this)
                                .setText("Okay!")
                                .setTextColor(Color.WHITE)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        Toast.makeText(mContext, "Welcome!", Toast.LENGTH_SHORT).show();
                                    }
                                }));

                if (position % 2 == 0) {
                    provider.setBackgroundResourceColor(R.color.color_0);
                }

                return provider.endConfig().build();
            }
            case 5: {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
                adapter.add("Hello");
                adapter.add("World");
                adapter.add("!");

                return new Card.Builder(this)
                        .setTag("LIST_CARD")
                        .setDismissible()
                        .withProvider(new ListCardProvider())
                        .setLayout(R.layout.material_list_card_layout)
                        .setTitle("List Card")
                        .setDescription("Take a list")
                        .setAdapter(adapter)
                        .endConfig()
                        .build();
            }
            default: {
                final CardProvider provider = new Card.Builder(this)
                        .setTag("BIG_IMAGE_BUTTONS_CARD")
                        .setDismissible()
                        .withProvider(new CardProvider())
                        .setLayout(R.layout.material_image_with_buttons_card)
                        .setTitle(title)
                        .setDescription(description)
                        .setDrawable(R.drawable.bauer_image1)
                        .addAction(R.id.left_text_button, new TextViewAction(this)
                                .setText("add card")
                                .setTextResourceColor(R.color.color_5)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        Log.d("ADDING", "CARD");

                                        mListView.getAdapter().add(generateNewCard());
                                        Toast.makeText(mContext, "Added new card", Toast.LENGTH_SHORT).show();
                                    }
                                }))
                        .addAction(R.id.right_text_button, new TextViewAction(this)
                                .setText("right button")
                                .setTextResourceColor(R.color.color_5)
                                .setListener(new OnActionClickListener() {
                                    @Override
                                    public void onActionClicked(View view, Card card) {
                                        Toast.makeText(mContext, "You have pressed the right button", Toast.LENGTH_SHORT).show();
                                    }
                                }));

                if (position % 2 == 0) {
                    provider.setDividerVisible(true);
                }

                return provider.endConfig().build();
            }
        }
    }

    private Card generateNewCard() {
        @SuppressLint("ResourceAsColor") final CardProvider provider = new Card.Builder(this)
                .setTag("BASIC_IMAGE_BUTTON_CARD")
                .setDismissible()
                .withProvider(new CardProvider<>())
                .setLayout(R.layout.material_basic_image_buttons_card_layout)
                .setTitle("title")
                .setTitleGravity(Gravity.END)
                .setDescription("description")
                .setDescriptionGravity(Gravity.END)
                .setDrawable(R.drawable.bauer_image1)
                .setDrawableConfiguration(new CardProvider.OnImageConfigListener() {
                    @Override
                    public void onImageConfigure(@NonNull RequestCreator requestCreator) {
                        requestCreator.fit();
                    }
                })
                .addAction(R.id.left_text_button, new TextViewAction(this)
                        .setText("left")
                        .setTextResourceColor(R.color.color_5)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Toast.makeText(mContext, "You have pressed the left button", Toast.LENGTH_SHORT).show();
                                card.getProvider().setTitle("CHANGED ON RUNTIME");
                            }
                        }))
                .addAction(R.id.right_text_button, new TextViewAction(this)
                        .setText("right")
                        .setTextResourceColor(R.color.color_5)
                        .setListener(new OnActionClickListener() {
                            @Override
                            public void onActionClicked(View view, Card card) {
                                Toast.makeText(mContext, "You have pressed the right button on card " + card.getProvider().getTitle(), Toast.LENGTH_SHORT).show();
                                card.dismiss();
                            }
                        }));


        return provider.endConfig().build();
    }


}
