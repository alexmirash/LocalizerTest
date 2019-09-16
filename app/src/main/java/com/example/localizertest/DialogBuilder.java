package com.example.localizertest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Mirash
 */

public class DialogBuilder {
    private CharSequence title;
    private CharSequence message;
    private CharSequence startButtonText;
    private CharSequence endButtonText;
    private Integer bottomImageSrc;
    private Integer topImageSrc;
    private View.OnClickListener startButtonClickListener;
    private View.OnClickListener endButtonClickListener;
    private DialogInterface.OnDismissListener dismissListener;
    private boolean isCancelable = true;
    private boolean isButtonsVertical;
    private Context activityContext;

    public DialogBuilder(Context context) {
        this.activityContext = context;
    }

    public DialogBuilder setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public DialogBuilder setMessage(CharSequence message) {
        this.message = message;
        return this;
    }

    public DialogBuilder setStartButtonText(CharSequence startButtonText) {
        this.startButtonText = startButtonText;
        return this;
    }

    public DialogBuilder setEndButtonText(CharSequence endButtonText) {
        this.endButtonText = endButtonText;
        return this;
    }

    public DialogBuilder setStartButtonOnClickListener(View.OnClickListener listener) {
        this.startButtonClickListener = listener;
        return this;
    }

    public DialogBuilder setEndButtonOnClickListener(View.OnClickListener listener) {
        this.endButtonClickListener = listener;
        return this;
    }

    public DialogBuilder setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public DialogBuilder setCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        return this;
    }

    public DialogBuilder setButtonsVertical(boolean buttonsVertical) {
        isButtonsVertical = buttonsVertical;
        return this;
    }

    public DialogBuilder setBottomImageSrc(int bottomImageSrc) {
        this.bottomImageSrc = bottomImageSrc;
        return this;
    }

    public DialogBuilder setTopImageSrc(int topImageSrc) {
        this.topImageSrc = topImageSrc;
        return this;
    }

    public AlertDialog show() {
        View dialogContentView = View.inflate(activityContext, R.layout.alert_dialog_content_view, null);
        ViewStub buttonsViewStub = dialogContentView.findViewById(isButtonsVertical ?
                R.id.alert_dialog_buttons_vertical : R.id.alert_dialog_buttons_horizontal);
        buttonsViewStub.inflate();
        AlertDialog dialog = new AlertDialog.Builder(activityContext)
                .setView(dialogContentView)
                .create();
        if (title != null) {
            TextView titleView = dialogContentView.findViewById(R.id.alert_dialog_title);
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(title);
        }
        TextView messageView = dialogContentView.findViewById(R.id.alert_dialog_message);
        if (message != null) {
            messageView.setText(message);
        } else {
            messageView.setVisibility(View.GONE);
        }
        TextView leftButton = dialogContentView.findViewById(R.id.alert_dialog_button_start);
        if (startButtonText != null) {
            leftButton.setText(startButtonText);
            leftButton.setOnClickListener(view -> {
                if (startButtonClickListener != null) {
                    startButtonClickListener.onClick(view);
                }
                dialog.dismiss();
            });
        } else {
            leftButton.setVisibility(View.GONE);
        }
        TextView rightButton = dialogContentView.findViewById(R.id.alert_dialog_button_end);
        if (endButtonText != null) {
            rightButton.setText(endButtonText);
            rightButton.setOnClickListener(view -> {
                if (endButtonClickListener != null) {
                    endButtonClickListener.onClick(view);
                }
                dialog.dismiss();
            });
        } else {
            rightButton.setVisibility(View.GONE);
        }
        if (bottomImageSrc != null) {
            ImageView imageView = dialogContentView.findViewById(R.id.alert_dialog_image_bottom);
            imageView.setImageResource(bottomImageSrc);
            imageView.setVisibility(View.VISIBLE);
        }
        if (topImageSrc != null) {
            ImageView imageView = dialogContentView.findViewById(R.id.alert_dialog_image_top);
            imageView.setImageResource(topImageSrc);
            imageView.setVisibility(View.VISIBLE);
        }
        if (dismissListener != null) {
            dialog.setOnDismissListener(dismissListener);
        }
        dialog.setCanceledOnTouchOutside(isCancelable);
        dialog.setCancelable(isCancelable);
        dialog.show();

        return dialog;
    }

    public static AlertDialog showOkCancelDialog(Context context, CharSequence message, View.OnClickListener okOnClickListener) {
        return new DialogBuilder(context)
                .setMessage(message)
                .setEndButtonText(context.getString(R.string.cancel))
                .setStartButtonText(context.getString(R.string.ok))
                .setStartButtonOnClickListener(okOnClickListener)
                .show();
    }
}
