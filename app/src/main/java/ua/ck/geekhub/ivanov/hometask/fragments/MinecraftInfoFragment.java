package ua.ck.geekhub.ivanov.hometask.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ua.ck.geekhub.ivanov.hometask.R;
import ua.ck.geekhub.ivanov.hometask.models.Tool;


public class MinecraftInfoFragment extends Fragment {

    private Tool currentTool;

    public static final String EXTRA_TOOL = "ua.ck.geekhub.ivanov.hometask.EXTRA_TOOL";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentTool = (Tool) getArguments().getSerializable(EXTRA_TOOL);
        setToolResult(currentTool);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minecraft_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView thumbnailView = (ImageView) view.findViewById(R.id.toolImageView);
        thumbnailView.setImageResource(currentTool.getImage());
        TextView messageView = (TextView) view.findViewById(R.id.descriptionTextView);
        String text = currentTool.getDescription();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        FlowTextHelper.tryFlowText(text, thumbnailView, messageView, display);
    }


    private void setToolResult(Tool tool) {
        Intent data = new Intent();
        data.putExtra("tool", tool);
        getActivity().setResult(Activity.RESULT_CANCELED, data);
    }

    public static MinecraftInfoFragment newInstance(Tool tool) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TOOL, tool);
        MinecraftInfoFragment fragment = new MinecraftInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}


class MyLeadingMarginSpan implements LeadingMarginSpan.LeadingMarginSpan2 {
    private int margin;
    private int lines;

    public MyLeadingMarginSpan(int lines, int margin) {
        this.margin = margin;
        this.lines = lines;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return first ? margin : 0;
    }


    public int getLeadingMarginLineCount() {
        return lines;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom, CharSequence text,
                                  int start, int end, boolean first, Layout layout) {}
}

class FlowTextHelper {
    private static boolean mNewClassAvailable  = true;

    public static void tryFlowText(String text, View thumbnailView, TextView messageView, Display display){
        // There is nothing I can do for older versions, so just return
        if(!mNewClassAvailable) return;

        // Get height and width of the image and height of the text line
        thumbnailView.measure(display.getWidth(), display.getHeight());
        int height = thumbnailView.getMeasuredHeight();
        int width = thumbnailView.getMeasuredWidth();
        float textLineHeight = messageView.getPaint().getTextSize();

        // Set the span according to the number of lines and width of the image
        int lines = (int) Math.round(height / textLineHeight) - 2;
        //For an html text you can use this line: SpannableStringBuilder ss = (SpannableStringBuilder)Html.fromHtml(text);
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new MyLeadingMarginSpan(lines, width), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        messageView.setText(ss);

        // Align the text with the image by removing the rule that the text is to the right of the image
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)messageView.getLayoutParams();
        int[]rules = params.getRules();
        rules[RelativeLayout.RIGHT_OF] = 0;
    }
}
