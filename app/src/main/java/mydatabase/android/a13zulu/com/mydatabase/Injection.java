package mydatabase.android.a13zulu.com.mydatabase;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsRepository;
import mydatabase.android.a13zulu.com.mydatabase.data.source.StorageRoomsRepository;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsRepository;
import mydatabase.android.a13zulu.com.mydatabase.data.source.local.ItemsLocalDataSource;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 *  at compile time.
 */

public class Injection {

    public static ItemsRepository provideItemsRepository(@NonNull Context context){
        checkNotNull(context);

        return ItemsRepository.getInstance(ItemsLocalDataSource.getInstance(context));
    }

    public static TransactionsRepository provideTransactionRepository(@NonNull Context context){
        checkNotNull(context);

        return TransactionsRepository.getInstance(ItemsLocalDataSource.getInstance(context));
    }

    public static StorageRoomsRepository provideStorageRoomsRepository(@Nonnull Context context){
        checkNotNull(context);

        return StorageRoomsRepository.getInstance(ItemsLocalDataSource.getInstance(context));
    }
}
