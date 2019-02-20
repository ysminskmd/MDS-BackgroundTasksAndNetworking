# MDS2019-Practical7
Examples for lesson "Background tasks and networking". All examples provided via tags. So you should checkout tag like branch. Full list of examples:
* thread_1 - long task on main thread;
* thread_2 - separate thread for long task and update UI from separate thread;
* thread_3 - separate thread for long task and update UI via main threan handler;
* thread_4 - separate handler thread for long task and update UI via runOnUiThread call;
* async_task - async task for long background task;
* image_loader_main - network request via UrlConnection from main thread;
* image_loader_async_task - network request via UrlConnection from async task;
* image_loader_okhttp3 - network request via OkHttp3 library from aync task;
* image_loader_okhttp3_async - async network request via OkHttp3 library;
* image_loader_service - service for network request;
* image_loader_intent_service - intent service for network request;
* image_loader_job_service - job service for network request;
* image_loader_job_intent_service - job intent service for network request;
* image_loader_work_manager - work manager for network request;
* factorial_calculator - work manager + room database + live data + view model for difficult background fetching and processing data for UI.
