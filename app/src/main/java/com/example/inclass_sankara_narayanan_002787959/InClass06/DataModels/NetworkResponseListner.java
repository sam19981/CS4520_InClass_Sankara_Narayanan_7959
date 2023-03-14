package com.example.inclass_sankara_narayanan_002787959.InClass06.DataModels;

import java.util.ArrayList;

public interface NetworkResponseListner {
    //Handle When We Recevie Success Data
    void SuccessData(ArrayList<News> news);

    //When We Received Fail Response
    void FailedData();

}
