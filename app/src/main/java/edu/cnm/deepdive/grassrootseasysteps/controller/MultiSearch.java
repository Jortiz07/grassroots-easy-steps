//package edu.cnm.deepdive.grassrootseasysteps.controller;
//
//import androidx.fragment.app.Fragment;
//import com.tomtom.online.sdk.search.OnlineSearchApi;
//import com.tomtom.online.sdk.search.SearchApi;
//import com.tomtom.online.sdk.search.api.SearchError;
//import com.tomtom.online.sdk.search.api.batch.BatchSearchResultListener;
//import com.tomtom.online.sdk.search.data.batch.BatchSearchQueryBuilder;
//import com.tomtom.online.sdk.search.data.batch.BatchSearchResponse;
//import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery;
//import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder;
//
//public abstract class MultiSearch extends Fragment implements SearchApi{
//
//  private final SearchApi searchApi = OnlineSearchApi.create(getContext());
//
//
//  private void multiSearch (String location) {
//    BatchSearchQueryBuilder batchQuery = new BatchSearchQueryBuilder();
//    batchQuery.withFuzzySearchQuery(query);
//    batchQuery.withFuzzySearchQuery(query);
//
//    BatchSearchResultListener batchSearchResultListener = new BatchSearchResultListener() {
//      @Override
//      public void onSearchResult(BatchSearchResponse batchSearchResponse) {
//
//      }
//
//      @Override
//      public void onSearchError(SearchError searchError) {
//
//      }
//    };
//    searchApi.batchSearch(batchQuery.build(), batchSearchResultListener);
//  }
//}
