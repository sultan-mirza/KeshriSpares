package kk.play.keshrispares.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class CustomRequest extends Request<JSONArray>{

	private Listener<JSONArray> listener;
    private Map<String, String> params;

    public CustomRequest(String url, Map<String, String> params,
              Listener<JSONArray> reponseListener, ErrorListener errorListener) {
          super(Method.GET, url, errorListener);
          this.listener = reponseListener;
          this.params = params;
    }

    public CustomRequest(int method, String url, Map<String, String> params,
              Listener<JSONArray> reponseListener, ErrorListener errorListener) {
          super(method, url, errorListener);
          this.listener = reponseListener;
          this.params = params;
      }
    public CustomRequest(int method, String url,
            Listener<JSONArray> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
    }

  @Override
  protected Map<String, String> getParams() throws AuthFailureError {
    return params;
  }

  @Override
  protected void deliverResponse(JSONArray response) {
      listener.onResponse(response);
  }

  @Override
  protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
       try {
              String jsonString = new String(response.data,
                      HttpHeaderParser.parseCharset(response.headers));
              return Response.success(new JSONArray(jsonString),
                      HttpHeaderParser.parseCacheHeaders(response));
          } catch (UnsupportedEncodingException e) {
              return Response.error(new ParseError(e));
          } catch (JSONException je) {
              return Response.error(new ParseError(je));
          }
  }
}
