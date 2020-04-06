# MDS2020-BackgroundTasksAndNetworking

* Thread1 - long task on main thread;
* Deadlock - deadlock;
* Thread2 - worker thread for long task and update UI from worker thread;
* Thread3 - separate thread for long task and update UI from main thread;
* Thread4 - @MainThread and @WorkerThread annotations;
* HandlerThread - HandlerThread usage;
* AsyncTask - async task for long background task;
* ConnectionTypes - getting active network info via CONNECTIVITY_MANAGER;
* DataSaver - getting DataSaver preferences;
* ImageLoaderMain - network request via UrlConnection from main thread;
* ImageLoaderAsyncTask - network request via UrlConnection from async task;
* ImageLoaderOkHttp - network request via OkHttp3 library from aync task;
* ImageLoaderOkHttpAsync - async network request via OkHttp3 library;
* ImageLoaderService - service for network request;
* ImageLoaderFgService - foreground service for network request;
* ImageLoaderIntentService - intent service for network request;
* ImageLoaderJobService - job service for network request;
* CalculatorJobIntentService - job intent service for long background task;
* ImageLoaderWorkManager - work manager for network request;
* ImageLoaderDownloadManager - DownloadManager for files download;
* FactorialCalculator - work manager + room database + live data + view model for difficult background fetching and processing data for UI.
